package com.wrh.meteo.component.quickhull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hxgis.draw.contour.OriginLonLatBound;
import com.hxgis.draw.contour.bean.FloatImageDiscreteData;
import com.hxgis.draw.contour.bean.FloatInterpolateType;
import com.hxgis.draw.contour.renderer.ContourImageRenderer;
import com.hxgis.draw.contour.renderer.DrawPhase;
import com.hxgis.draw.contour.renderer.FloatContourImageRenderer;
import com.hxgis.draw.core.Board;
import com.hxgis.draw.core.Offset;
import com.hxgis.draw.core.PolygonEx;
import com.hxgis.draw.core.PositionDescriber;
import com.hxgis.draw.core.feature.Circle;
import com.hxgis.draw.core.feature.JavaShape;
import com.hxgis.draw.core.panel.LonLatPanel;
import com.hxgis.draw.core.panel.XYPanel;
import com.hxgis.meteodata.comdata.GridData;
import com.wrh.meteo.component.enums.ProInfoEnum;
import com.wrh.meteo.component.help.JFrameHelp;
import com.wrh.meteo.component.quickhull.datastructures.LinkedList;
import com.wrh.meteo.component.quickhull.datastructures.LinkedListNode;
import com.wrh.meteo.draw.DrawImg;
import org.geojson.FeatureCollection;
import org.geojson.LngLatAlt;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author wrh
 * @version 1.0
 * @date 2021/2/25 11:34
 * @describe
 */
class QuickHullTest {

    @Test
    void useAlgorithm() throws JsonProcessingException {

        List<Point2D.Double> points = new ArrayList<>();
        points.add(new Point2D.Double(111.58, 31.64));
        points.add(new Point2D.Double(111.76, 32.04));
        points.add(new Point2D.Double(112.37, 31.99));
        points.add(new Point2D.Double(112.67, 32.23));
        points.add(new Point2D.Double(112.99, 31.47));
        points.add(new Point2D.Double(112.53, 31.16));
        points.add(new Point2D.Double(112.37, 30.27));
        points.add(new Point2D.Double(112.21, 30.45));
        points.add(new Point2D.Double(111.31, 30.63));
        points.add(new Point2D.Double(110.8, 31.48));

        ProInfoEnum proInfoEnum = ProInfoEnum.BCWH;
        FeatureCollection boundFeatures = DrawImg.getBoundFeatures(proInfoEnum);
        OriginLonLatBound lonLatBound = OriginLonLatBound.from(DrawImg.getImgWidth(),
                proInfoEnum.getMinLat(), proInfoEnum.getMinLon(), proInfoEnum.getMaxLat(), proInfoEnum.getMaxLon());
        Board board = new Board(DrawImg.getImgWidth(), DrawImg.getImgHeight());
        //初始化经纬度面板
        XYPanel xyPanel = new XYPanel(DrawImg.getImgWidth(), DrawImg.getImgHeight());
        LonLatPanel lonLatPanel = new LonLatPanel(lonLatBound);
        board.addPanel(lonLatPanel, PositionDescriber.xy(0, 0));
        board.addPanel(xyPanel, PositionDescriber.xy(0, 0));
        lonLatPanel.addShape(boundFeatures);

        // 凸包计算
        QuickHull quickHull = new QuickHull();
        LinkedList linkedList = new LinkedList();
        points.forEach(linkedList::insert);
        LinkedList calcPoints = quickHull.useAlgorithm(linkedList);
        LinkedListNode node = calcPoints.getHead();
        PolygonEx polygonEx = new PolygonEx();
        while (node != null) {
            Point2D.Double point = node.getPoint();
            polygonEx.addPoint((float) point.getX(), (float) point.getY());
            node = node.getNext();
        }
        lonLatPanel.addShape(polygonEx, null, 1, Color.red);

        Circle circle = new Circle(3, 1, Color.BLACK, new Color(255, 147, 19));
        points.forEach(point -> {
            lonLatPanel.addShape(circle, (float) point.getY(), (float) point.getX(), Offset.of(0, 0));
        });

        JFrameHelp.showImageFrame(board.draw());
    }

}