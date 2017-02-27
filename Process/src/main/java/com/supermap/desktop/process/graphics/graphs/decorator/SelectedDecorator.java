package com.supermap.desktop.process.graphics.graphs.decorator;

import com.supermap.desktop.process.graphics.GraphCanvas;
import com.supermap.desktop.process.graphics.graphs.IGraph;
import com.supermap.desktop.process.graphics.graphs.decorator.AbstractDecorator;

import java.awt.*;

/**
 * Created by highsad on 2017/2/23.
 */
public class SelectedDecorator extends AbstractDecorator {

	public SelectedDecorator(GraphCanvas canvas) {
		super(canvas);
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}

	@Override
	public boolean contains(Point point) {
		return false;
	}

	@Override
	public IGraph clone() {
		return null;
	}
}
