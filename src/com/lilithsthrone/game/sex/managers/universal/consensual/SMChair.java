package com.lilithsthrone.game.sex.managers.universal.consensual;

import java.util.Map;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.sex.SexPositionNew;
import com.lilithsthrone.game.sex.SexPositionSlot;
import com.lilithsthrone.game.sex.managers.SexManagerDefault;

/**
 * @since 0.1.69.9
 * @version 0.1.97
 * @author Innoxia
 */
public class SMChair extends SexManagerDefault {

	public SMChair(Map<GameCharacter, SexPositionSlot> dominants, Map<GameCharacter, SexPositionSlot> submissives) {
		super(SexPositionNew.CHAIR_SEX,
				dominants,
				submissives);
	}

}
