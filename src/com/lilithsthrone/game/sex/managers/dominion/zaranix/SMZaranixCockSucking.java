package com.lilithsthrone.game.sex.managers.dominion.zaranix;

import java.util.Map;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.sex.SexPositionNew;
import com.lilithsthrone.game.sex.SexPositionSlot;
import com.lilithsthrone.game.sex.managers.SexManagerDefault;

/**
 * @since 0.1.95
 * @version 0.1.97
 * @author Innoxia
 */
public class SMZaranixCockSucking extends SexManagerDefault {

	public SMZaranixCockSucking(Map<GameCharacter, SexPositionSlot> dominants, Map<GameCharacter, SexPositionSlot> submissives) {
		super(SexPositionNew.KNEELING_ORAL_ZARANIX,
				dominants,
				submissives);
	}

}
