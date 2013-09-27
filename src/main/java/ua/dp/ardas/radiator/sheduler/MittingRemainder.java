package ua.dp.ardas.radiator.sheduler;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ua.dp.ardas.radiator.jobs.play.sound.SoundController;

@Component
public class MittingRemainder {
	private static Logger LOG = Logger.getLogger(MittingRemainder.class.getName());
	
	@Autowired
	private SoundController soundController;
	@Value("${mitting.remainder.sound}")
	private String pathToSound;

	
	@Scheduled(cron="${mitting.remainder.cron}")
	private void  executeTask() {
		LOG.info("Need sound");
		soundController.registerSoundPath(pathToSound);
	}
}