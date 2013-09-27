package ua.dp.ardas.radiator.jobs.play.sound;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class SoundController {

	private List<String> soundPathes = newArrayList();

	public void registerSoundPath(String soundPath){
		if (isNullOrEmpty(soundPath)) {
			return;
		}
		
		soundPathes.add(soundPath);
	}
	
	public List<String> getSoundPathes() {
		List<String> result = soundPathes;
		
		soundPathes = newArrayList();
		
		return result;
	}
}
