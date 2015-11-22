/**
 * ImageCash.java
 */
package org.crf.tr.ui.images;

import static java.lang.String.format;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author cvirtue
 * @version "%I, %G"
 */
public final class ImageCash {

	ImageCash() {
		super( );
		_cash = new HashMap<>();
	}

	public static final ImageCash instance() {
		return InstanceHolder._instance;
	}

	public final ImageView get(final String name) {
		return get( name, _defaultWidth, _defaultHeight );
	}

	public final ImageView get(final String name, final int w, final int h) {
		final ImageView image = _cash.get( name );
		if (image == null) {
			final ImageView img = new ImageView(new Image(format( _imageSourcesURIFormat, name ), w, h, false, true ));
			_cash.put( name, img );
			return img;
		}
		return image;

	}


	public static final String _imageSourcesURIFormat = "file:src/main/resources/images/%s";
	public static final int _defaultHeight = 16;
	public static final int _defaultWidth  = 16;
	private final Map<String, ImageView> _cash;


	static final class InstanceHolder {
		static final ImageCash _instance = new ImageCash();
	}
}
