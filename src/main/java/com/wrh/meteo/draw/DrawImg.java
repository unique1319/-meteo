package com.wrh.meteo.draw;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hxgis.draw.contour.*;
import com.hxgis.draw.contour.bean.FloatImageDiscreteData;
import com.hxgis.draw.contour.bean.FloatInterpolateType;
import com.hxgis.draw.contour.bean.SimpleDiscreteData;
import com.hxgis.draw.contour.renderer.ContourImageRenderer;
import com.hxgis.draw.contour.renderer.DrawPhase;
import com.hxgis.draw.contour.renderer.FloatContourImageRenderer;
import com.hxgis.draw.core.FontType;
import com.hxgis.draw.core.Offset;
import com.hxgis.draw.core.TextStyle;
import com.hxgis.draw.core.feature.Circle;
import com.hxgis.draw.core.feature.Text;
import com.hxgis.draw.core.geojson.GeoFeatureStyle;
import com.hxgis.draw.core.panel.LonLatPanel;
import com.hxgis.draw.core.util.ColorUtil;
import com.hxgis.meteodata.comdata.GridData;
import com.wrh.meteo.component.enums.LegendEnum;
import com.wrh.meteo.component.enums.ProInfoEnum;
import com.wrh.meteo.component.geo.ReadProGeoJson;
import lombok.Getter;
import org.geojson.Feature;
import org.geojson.FeatureCollection;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * @author wrh
 * @version 1.0
 * @date 2021/2/24 13:47
 * @describe
 */
public class DrawImg {

    @Getter
    private static final int imgWidth;
    @Getter
    private static final int imgHeight;

    static {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        imgWidth = (int) (screenSize.getWidth() * 0.7);
        imgHeight = (int) (screenSize.getHeight() * 0.7);
    }

    /**
     * 绘制格点（色斑 && 填色）
     *
     * @param grid           格点数据
     * @param proInfoEnum    省份枚举
     * @param legendEnum     色卡枚举
     * @param isCover        是否允许边界覆盖
     * @param isoTracerClass 渲染类型：GeoJson||格点填色
     * @param <T>            IsoTracer实现类
     * @return BufferedImage
     * @throws Exception 绘制异常
     */
    public static <T> BufferedImage drawGridData(GridData grid, ProInfoEnum proInfoEnum, LegendEnum legendEnum, boolean isCover, Class<T> isoTracerClass) throws Exception {
        FeatureCollection boundFeatures = getBoundFeatures(proInfoEnum);
        OriginLonLatBound lonLatBound = OriginLonLatBound.from(imgWidth,
                proInfoEnum.getMinLat(), proInfoEnum.getMinLon(), proInfoEnum.getMaxLat(), proInfoEnum.getMaxLon());
        ContourImageRenderer<FloatImageDiscreteData, FloatInterpolateType> contourImageRenderer = new FloatContourImageRenderer<>(imgWidth, imgHeight, lonLatBound);

        IsoTracer isoTracer = null;
        if (PixelIsoTracer.class.equals(isoTracerClass)) {
            isoTracer = new PixelIsoTracer(legendEnum.getIsoValues(), ColorUtil.hexToRgb(legendEnum.getColors()));
        } else if (WContourIsoTracer.class.equals(isoTracerClass)) {
            isoTracer = new WContourIsoTracer(legendEnum.getIsoValues(), ColorUtil.hexToRgb(legendEnum.getColors()));
            Interpolater interpolater = new WContourInterpolater(lonLatBound, 0.02, 0.02);
            contourImageRenderer.setInterpolater(interpolater);
        } else {
            throw new Exception("不支持的IsoTracer：" + isoTracerClass);
        }
        if (isCover) contourImageRenderer.setCoverGeojson(boundFeatures);
        contourImageRenderer.setIsoTracer(isoTracer);//等值线生成器（拟合生成等值线）
        // 绘制边界
        contourImageRenderer.addAfterDrawIntercepter(DrawPhase.draw_contour, (rootPanel, lonLatPanel, board) -> lonLatPanel.addShape(boundFeatures));
        return contourImageRenderer.render(grid);
    }

    /**
     * 绘制站点（打点图）
     *
     * @param discreteData 离散点数据
     * @param proInfoEnum  省份枚举
     * @param isCover      是否允许边界覆盖
     * @return BufferedImage
     * @throws Exception 绘制异常
     */
    public static BufferedImage drawPointData(List<FloatImageDiscreteData> discreteData, ProInfoEnum proInfoEnum, boolean isCover) throws Exception {
        FeatureCollection boundFeatures = getBoundFeatures(proInfoEnum);
        OriginLonLatBound lonLatBound = OriginLonLatBound.from(imgWidth,
                proInfoEnum.getMinLat(), proInfoEnum.getMinLon(), proInfoEnum.getMaxLat(), proInfoEnum.getMaxLon());
        ContourImageRenderer<FloatImageDiscreteData, FloatInterpolateType> contourImageRenderer = new FloatContourImageRenderer<>(imgWidth, imgHeight, lonLatBound);

        TextStyle markerValueTextStyle = new TextStyle(FontType.YA_HEI, 10, Font.PLAIN, Color.BLACK);
        contourImageRenderer
                .setOnDrawDiscreteMarker(new ContourImageRenderer.OnDrawDiscreteMarker() {
                    @Override
                    public void draw(LonLatPanel lonLatPanel, SimpleDiscreteData simpleDiscreteData) {
                        Text val = new Text(String.valueOf(simpleDiscreteData.getVal().getValue()), markerValueTextStyle);
                        lonLatPanel.addShape(val, simpleDiscreteData.getLat(), simpleDiscreteData.getLon(), Offset.of(15, 0));
                        Circle circle = new Circle(3, 1, Color.BLACK, new Color(255, 147, 19));
                        lonLatPanel.addShape(circle, simpleDiscreteData.getLat(), simpleDiscreteData.getLon(), Offset.of(0, 0));
                    }
                });
        if (isCover) contourImageRenderer.setCoverGeojson(boundFeatures);
        contourImageRenderer.addAfterDrawIntercepter(DrawPhase.draw_contour, (rootPanel, lonLatPanel, board) -> lonLatPanel.addShape(boundFeatures));
        return contourImageRenderer.render(discreteData);
    }


    public static BufferedImage drawPointData(List<FloatImageDiscreteData> discreteData, ProInfoEnum proInfoEnum, LegendEnum legendEnum, boolean isCover) throws Exception {
        FeatureCollection boundFeatures = getBoundFeatures(proInfoEnum);
        OriginLonLatBound lonLatBound = OriginLonLatBound.from(imgWidth,
                proInfoEnum.getMinLat(), proInfoEnum.getMinLon(), proInfoEnum.getMaxLat(), proInfoEnum.getMaxLon());
        ContourImageRenderer<FloatImageDiscreteData, FloatInterpolateType> contourImageRenderer = new FloatContourImageRenderer<>(imgWidth, imgHeight, lonLatBound);

        IsoTracer isoTracer = new WContourIsoTracer(legendEnum.getIsoValues(), ColorUtil.hexToRgb(legendEnum.getColors()));
        Interpolater interpolater = new WContourInterpolater(lonLatBound, 0.02, 0.02);
        contourImageRenderer.setInterpolater(interpolater);
        if (isCover) contourImageRenderer.setCoverGeojson(boundFeatures);
        contourImageRenderer.setIsoTracer(isoTracer);//等值线生成器（拟合生成等值线）
        // 绘制边界
        contourImageRenderer.addAfterDrawIntercepter(DrawPhase.draw_contour, (rootPanel, lonLatPanel, board) -> lonLatPanel.addShape(boundFeatures));
        return contourImageRenderer.render(discreteData);
    }


    /**
     * 获取绘图边界
     *
     * @param proInfoEnum 省份枚举
     * @return FeatureCollection
     * @throws JsonProcessingException Json解析异常
     */
    public static FeatureCollection getBoundFeatures(ProInfoEnum proInfoEnum) throws JsonProcessingException {
        String bound = ReadProGeoJson.getBoundaryGeoJsonStringRead(proInfoEnum.getAdminCode());
        ObjectMapper objectMapper = new ObjectMapper();
        FeatureCollection boundFeatures = objectMapper.readValue(bound, FeatureCollection.class);
        GeoFeatureStyle boundaryStyle = GeoFeatureStyle.getDefault().fill(false).stroke(1, Color.BLACK);
        for (Feature boundFeature : boundFeatures) {
            boundaryStyle.write(boundFeature);
        }
        return boundFeatures;
    }
}
