package com.supermap.desktop.controls.utilties;

import com.supermap.data.Colors;
import com.supermap.desktop.Application;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * @author XiaJT
 */
public class ColorsUtilties {
	/**
	 * 生成随机色
	 *
	 * @param colorCount 颜色总数
	 * @param keyColors  关键色
	 * @return
	 */
	public static Colors buildRandom(int colorCount, Color[] keyColors) {
		Colors colors = new Colors();
		// 要产生的颜色比关键色还要少，太可怜了
		// 就不再进行随机色生成了，直接将随机色扔出去算了
		if (colorCount <= keyColors.length) {
			for (int index = 0; index < colorCount; index++) {
				colors.add(keyColors[index]);
			}
		} else {
			// 按照关键色计算每段需要生成多少随机色
			int colorCountPerSection = (int) (Math.floor(colorCount * 1.0 / (keyColors.length - 1)));
			int colorCountLastSection = colorCount - colorCountPerSection * (keyColors.length - 1);
			for (int index = 0; index < (keyColors.length - 1); index++) {
				colors.addRange(build(colorCountPerSection, keyColors[index], keyColors[index + 1]));
			}
			if (colorCountLastSection > 0) {
				colors.addRange(build(colorCountLastSection, keyColors[keyColors.length - 2], keyColors[keyColors.length - 1]));
			}
		}

		return colors;
	}

	private static Color[] build(int colorCount, Color startColor, Color endColor) {
		java.util.List<Color> colors = new ArrayList<>();

		try {
			float[] startHSV = Color.RGBtoHSB(startColor.getRed(), startColor.getGreen(), startColor.getBlue(), null);
			float[] endHSV = Color.RGBtoHSB(endColor.getRed(), endColor.getGreen(), endColor.getBlue(), null);

			colors.addAll(bulid(colorCount / 2, 12, startHSV));
			colors.addAll(bulid(colorCount - colorCount / 2, 12, endHSV));
		} catch (Exception ex) {
			Application.getActiveApplication().getOutput().output(ex);
		}

		return (Color[]) colors.toArray(new Color[colors.size()]);
	}

	/// <summary>
	///
	/// </summary>
	/// <param name="colorCount"></param>
	/// <param name="hueCount"></param>
	/// <param name="colorHSV"></param>
	/// <returns></returns>

	/**
	 * 指定色调区块取随机色
	 */
	private static List<Color> bulid(int colorCount, int hueCount, float[] colorHSV) {
		List<Color> colors = new ArrayList<>();
		try {
			double increment = 360 / hueCount;

			if (colorHSV[2] < Math.pow(Math.E, -10)
					|| (Math.abs(colorHSV[1] - 1.0) < Math.pow(Math.E, -10) && colorHSV[1] < Math.pow(Math.E, -10))) {
				colors.addAll(Build(colorCount, colorHSV[0], colorHSV[0], colorHSV[1], colorHSV[1], 1, 0));
			} else {
				int currentSegment = (int) Math.floor(colorHSV[0] / increment);
				colors.addAll(Build(colorCount, increment * (currentSegment + 1), increment * currentSegment, 1, 0, colorHSV[2], colorHSV[2]));
			}
		} catch (Exception ex) {
			Application.getActiveApplication().getOutput().output(ex);
		}

		return colors;
	}

	public static List<Color> Build(int colorCount,
	                                double endHue,
	                                double startHue,
	                                double maxSaturation,
	                                double minSaturation,
	                                double maxValue,
	                                double minValue) {
		if (endHue > 360) {
			endHue = 360;
		}

		if (startHue < 0) {
			startHue = 0;
		}

		if (maxSaturation > 1) {
			maxSaturation = 1;
		}

		if (minSaturation < 0) {
			minSaturation = 0;
		}

		if (maxValue > 1) {
			maxValue = 1;
		}

		if (minValue < 0) {
			minValue = 0;
		}

		List<Color> colors = new ArrayList<>();
		Random random = new Random();

		int tempMaxSaturation = ((int) (maxSaturation * 100));
		int tempMinSaturation = (int) (minSaturation * 100);

		int tempMaxValue = (int) (maxValue * 100);
		int tempMinValue = (int) (minValue * 100);

		int tempEndHue = (int) (endHue * 100);
		int tempStartHue = (int) (startHue * 100);

		for (int index = 0; index < colorCount; index++) {
			int tempS = tempMaxSaturation - tempMinSaturation;
			if (tempS == 0) {
				tempS = 1;
			}
			int saturation = random.nextInt(tempS) + tempMinSaturation;
			int tempValue = tempMaxValue - tempMinValue;
			if (tempValue == 0) {
				tempValue = 1;
			}
			int value = random.nextInt(tempValue) + tempMinValue;

			int hue = 0;

			if (endHue < startHue) {
				int tempH = 36000 + tempEndHue - tempStartHue;
				if (tempH == 0) {
					tempH = 1;
				}
				hue = random.nextInt(tempH) + tempStartHue;
				if (hue > 36000) {
					hue = hue - 36000;
				}
			} else {
				int tempH = tempEndHue - tempStartHue;
				if (tempH == 0) {
					tempH = 1;
				}
				hue = random.nextInt(tempH) + tempStartHue;
			}

			colors.add(Color.getHSBColor((float) (hue * 0.01), (float) (saturation * 0.01), (float) (value * 0.01)));
		}

		return colors;
	}
}
