// Created by plusminus on 00:23:14 - 03.10.2008
package org.osmdroid.samples;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.MinimapOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

/**
 *
 * @author Nicolas Gramlich
 *
 */
public class SampleWithMinimapItemizedoverlay extends Activity {

	// ===========================================================
	// Constants
	// ===========================================================

	private static final int MENU_ZOOMIN_ID = Menu.FIRST;
	private static final int MENU_ZOOMOUT_ID = MENU_ZOOMIN_ID + 1;

	// ===========================================================
	// Fields
	// ===========================================================

	private MapView mOsmv;
	private ItemizedOverlay<OverlayItem> mMyLocationOverlay;

	// ===========================================================
	// Constructors
	// ===========================================================
	/** Called when the activity is first created. */
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final RelativeLayout rl = new RelativeLayout(this);

		this.mOsmv = new MapView(this);
		this.mOsmv.setTilesScaledToDpi(true);
		rl.addView(this.mOsmv, new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));

		/* Itemized Overlay */
		{
			/* Create a static ItemizedOverlay showing a some Markers on some cities. */
			final ArrayList<OverlayItem> items = new ArrayList<>();
			items.add(new OverlayItem("Hannover", "SampleDescription", new GeoPoint(52370816, 9735936)));
			items.add(new OverlayItem("Berlin", "SampleDescription", new GeoPoint(52518333, 13408333)));
			items.add(new OverlayItem("Washington", "SampleDescription", new GeoPoint(38895000, -77036667)));
			items.add(new OverlayItem("San Francisco", "SampleDescription", new GeoPoint(37779300, -122419200)));
			items.add(new OverlayItem("Tolaga Bay", "SampleDescription", new GeoPoint(-38371000, 178298000)));

			/* OnTapListener for the Markers, shows a simple Toast. */
			this.mMyLocationOverlay = new ItemizedIconOverlay<>(items,
					new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
						@Override
						public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
							Toast.makeText(
									SampleWithMinimapItemizedoverlay.this,
									"Item '" + item.getTitle() + "' (index=" + index
											+ ") got single tapped up", Toast.LENGTH_LONG).show();
							return true; // We 'handled' this event.
						}

						@Override
						public boolean onItemLongPress(final int index, final OverlayItem item) {
							Toast.makeText(
									SampleWithMinimapItemizedoverlay.this,
									"Item '" + item.getTitle() + "' (index=" + index
											+ ") got long pressed", Toast.LENGTH_LONG).show();
							return false;
						}
					}, getApplicationContext());
			this.mOsmv.getOverlays().add(this.mMyLocationOverlay);
		}

		/* MiniMap */
		{
			final MinimapOverlay miniMapOverlay = new MinimapOverlay(this,
					mOsmv.getTileRequestCompleteHandler());
			this.mOsmv.getOverlays().add(miniMapOverlay);
		}

		this.setContentView(rl);

		// Default location and zoom level
		IMapController mapController = mOsmv.getController();
		mapController.setZoom(5);
		GeoPoint startPoint = new GeoPoint(50.936255, 6.957779);
		mapController.setCenter(startPoint);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods from SuperClass/Interfaces
	// ===========================================================

	@Override
	public boolean onCreateOptionsMenu(final Menu pMenu) {
		pMenu.add(0, MENU_ZOOMIN_ID, Menu.NONE, "ZoomIn");
		pMenu.add(0, MENU_ZOOMOUT_ID, Menu.NONE, "ZoomOut");

		return true;
	}

	@Override
	public boolean onMenuItemSelected(final int featureId, final MenuItem item) {
		switch (item.getItemId()) {
		case MENU_ZOOMIN_ID:
			this.mOsmv.getController().zoomIn();
			return true;

		case MENU_ZOOMOUT_ID:
			this.mOsmv.getController().zoomOut();
			return true;
		}
		return false;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}