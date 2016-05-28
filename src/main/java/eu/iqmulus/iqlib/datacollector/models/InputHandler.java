package eu.iqmulus.iqlib.datacollector.models;

import java.io.File;

public interface InputHandler {
	public void collect(File file, File withConfig);
}
