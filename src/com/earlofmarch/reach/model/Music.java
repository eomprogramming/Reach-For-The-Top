package com.earlofmarch.reach.model;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * The <code>Music</code> class is an easy-to-use implementation of the Java Sound API's
 * <code>{@link Clip}</code> to play audio.
 * @author Kenneth
 * Modified by @author Aly
 *
 */
public class Music {
	
	private static Clip musicClip;
	
	/**
	 * Creates a new audio Clip with the specified audio file
	 * @param url The name of the audio file
	 */
	public static void playMusic(URL url) {
		try {
			musicClip = AudioSystem.getClip();
			AudioInputStream stream = AudioSystem.getAudioInputStream(url);
			musicClip.open(stream);
			musicClip.start();
			
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
}
