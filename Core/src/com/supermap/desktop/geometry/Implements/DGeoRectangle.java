package com.supermap.desktop.geometry.Implements;

import com.supermap.data.Geometry;
import com.supermap.data.Point2Ds;
import com.supermap.desktop.geometry.Abstract.AbstractGeometry;
import com.supermap.desktop.geometry.Abstract.ILineConvertor;
import com.supermap.desktop.geometry.Abstract.IRegionConvertor;
import com.supermap.desktop.geometry.Abstract.IRegionFeature;

public class DGeoRectangle extends AbstractGeometry implements IRegionFeature, ILineConvertor, IRegionConvertor {

	protected DGeoRectangle(Geometry geometry) {
		super(geometry);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Geometry convertToRegion(int segment) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Geometry convertToLine(int segment) {
		// TODO Auto-generated method stub
		return null;
	}
}
