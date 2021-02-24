package com.wrh.meteo.draw;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hxgis.draw.contour.*;
import com.hxgis.draw.contour.bean.FloatImageDiscreteData;
import com.hxgis.draw.contour.bean.FloatInterpolateType;
import com.hxgis.draw.contour.renderer.ContourImageRenderer;
import com.hxgis.draw.contour.renderer.DrawPhase;
import com.hxgis.draw.contour.renderer.FloatContourImageRenderer;
import com.hxgis.draw.core.geojson.GeoFeatureStyle;
import com.hxgis.draw.core.util.ColorUtil;
import com.hxgis.meteodata.comdata.GridData;
import com.wrh.meteo.component.enums.LegendEnum;
import com.wrh.meteo.component.enums.ProInfoEnum;
import com.wrh.meteo.component.geo.ReadProGeoJson;
import com.wrh.meteo.component.help.JFrameHelp;
import com.wrh.meteo.read.ReadGrb2File;
import org.geojson.Feature;
import org.geojson.FeatureCollection;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @author wrh
 * @version 1.0
 * @date 2021/2/24 13:47
 * @describe
 */
public class DrawImg {

    private static final int imgWidth = 700;
    private static final int imgHeight = 500;

    /**
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
        String bound = ReadProGeoJson.getBoundaryGeoJsonStringRead(proInfoEnum.getAdminCode());
        ObjectMapper objectMapper = new ObjectMapper();
        FeatureCollection boundFeatures = objectMapper.readValue(bound, FeatureCollection.class);
        GeoFeatureStyle boundaryStyle = GeoFeatureStyle.getDefault().fill(false).stroke(1, Color.BLACK);
        for (Feature boundFeature : boundFeatures) {
            boundaryStyle.write(boundFeature);
        }

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

    public static void main(String[] args) throws Exception {
        String filePath = "E:\\ART\\PRE_FAST_DAY\\20191202\\Z_SURF_C_BABJ_20191202081101_P_CMPA_FAST_CHN_0P05_DAY-PRE-2019120208.GRB2";
        GridData gridData = ReadGrb2File.read(new File(filePath)).get(0);
        gridData = gridData.cut(ProInfoEnum.BCWH.getMinLon(), ProInfoEnum.BCWH.getMinLat(), ProInfoEnum.BCWH.getMaxLon(), ProInfoEnum.BCWH.getMaxLat());
        JFrameHelp.showImageFrame(drawGridData(gridData, ProInfoEnum.BCWH, LegendEnum.RAIN_1H, false, WContourIsoTracer.class));
    }

}
