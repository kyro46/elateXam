package com.spiru.dev.compareTextTask_addon.Utils;

import java.awt.Adjustable;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

/**
 * ScrollBarAdjustmentListener needed to be able to
 * synchronize scrolling between two TextAreas
 * 
 * @see: http://www.exampledepot.com/egs/javax.swing/scroll_SpEvt.html
 */

public class ScrollBarAdjustmentListener implements AdjustmentListener {

	@Override
	public void adjustmentValueChanged(AdjustmentEvent evt) {
		// This method is called whenever the value of a scrollbar is changed,
		// either by the user or programmatically.
		Adjustable source = evt.getAdjustable();

		// getValueIsAdjusting() returns true if the user is currently
		// dragging the scrollbar's knob and has not picked a final value
		if (evt.getValueIsAdjusting()) {
			// The user is dragging the knob
			return;
		}

		// Determine which scrollbar fired the event
		int orient = source.getOrientation();
		if (orient == Adjustable.HORIZONTAL) {
			// Event from horizontal scrollbar
		} else {
			// Event from vertical scrollbar
		}

		// Determine the type of event
		int type = evt.getAdjustmentType();
		switch (type) {
		case AdjustmentEvent.UNIT_INCREMENT:
			// Scrollbar was increased by one unit
			break;
		case AdjustmentEvent.UNIT_DECREMENT:
			// Scrollbar was decreased by one unit
			break;
		case AdjustmentEvent.BLOCK_INCREMENT:
			// Scrollbar was increased by one block
			break;
		case AdjustmentEvent.BLOCK_DECREMENT:
			// Scrollbar was decreased by one block
			break;
		case AdjustmentEvent.TRACK:
			// The knob on the scrollbar was dragged
			break;
		}

		// Get current value
		int value = evt.getValue();
	}

}
