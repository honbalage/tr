/**
 * Images.java
 */
package org.crf.tr.ui.images;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public final class Images {

	public static final ImageView get(final String name) {
		return ImageCash.instance().get( name );
	}

	public static final ImageView get(final String name, final int w, final int h) {
		return ImageCash.instance().get( name, w, h );
	}

	public static final Image imageOf(final String name, final int w, final int h) {
		return ImageCash.instance().imageOf( name, w, h );
	}

	public static final ImageView viewOf(final String name, final int w, final int h) {
		return ImageCash.instance().viewOf( name, w, h );
	}

	private Images() {
		super( );
	}
}
