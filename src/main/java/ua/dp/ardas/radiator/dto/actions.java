package ua.dp.ardas.radiator.dto;



import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;


@JsonAutoDetect
public class actions extends ArrayList {
	List<Causes> causes;
}
