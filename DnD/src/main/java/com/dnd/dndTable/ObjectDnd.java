package com.dnd.dndTable;

import com.dnd.dndTable.creatingDndObject.bagDnd.Items;
import com.dnd.dndTable.creatingDndObject.workmanship.Possession;
import com.dnd.dndTable.creatingDndObject.workmanship.features.Feature;
import com.dnd.dndTable.rolls.AttackModification;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME,  property = "OBJECT_DND")
@JsonSubTypes({
	@JsonSubTypes.Type(value = Items.class, name = "ITEM"),
	@JsonSubTypes.Type(value = Feature.class, name = "FEATURE"),
	@JsonSubTypes.Type(value = Possession.class, name = "POSSESSION"),
	@JsonSubTypes.Type(value = AttackModification.class, name = "ATTACK_MODIFICATION")})
public interface ObjectDnd extends DndKeyWallet
{
	public abstract long key();
}
