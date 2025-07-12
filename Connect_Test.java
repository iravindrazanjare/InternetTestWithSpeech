package conn_Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;

import javax.speech.AudioException;
import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.EngineStateError;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

public class Connect_Test {
	public static void main(String[] args) throws IllegalArgumentException, EngineException, AudioException, EngineStateError, InterruptedException{
		Synthesizer synthesizer = null;
		Boolean disconnected = true;
		try {
		System.setProperty("freetts.voices","com.sun.speech.freetts.en.us"+ ".cmu_us_kal.KevinVoiceDirectory");
		 Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
		 synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));
		 synthesizer.allocate();
		 synthesizer.resume();
		 do {
			try {
			URL url = new URL("https://www.google.com");
			long startTime = System.currentTimeMillis();
			URLConnection connection = url.openConnection();
			connection.connect();
			disconnected = false;
			long endTime = System.currentTimeMillis() - startTime;
				if(endTime > 10000) {
					synthesizer.speakPlainText("Internet Connection Very Slow, Try Switching to Other Network", null);
					disconnected = true;
				}
			}catch(MalformedURLException e) {
				synthesizer.speakPlainText("Internet is not Connected", null);
			}catch(IOException e) {
				synthesizer.speakPlainText("Internet is not Connected", null);
			}
			Thread.sleep(60 * 100);
		 }while(disconnected);
			synthesizer.speakPlainText("Internet is Connected, System is back Online",null);
			synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
		     synthesizer.deallocate();
		     System.exit(0);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
