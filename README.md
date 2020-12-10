#  Code Kata - Simple RPG

## TODO
* Review HP logic
* Add fight logic


## Req

The simple RPG game you're implementing allows to simulate the battle between 2 different characters to define the winner.

There are 3 types of characters: Paladins, Rogues, and Wizards. Paladins are stronger than Rogues, which are stronger than Wizards, which themselves are stronger than Paladins.

Defining who's winning a fight is really easy: characters only have one attack which deals a range of damage (for example, Rogues' attacks will deal from 5 to 8 damages). Those damages will be increased depending on the character's power, but lowered by the opponent's resistance.

The attacks are done simultaneously, which means equivalent characters may die together during the fight.

The formula we'll be using for the attack is the following one:

```
total_damage = (attack_damage * attacker_power) / defenser_resistance  
```

As we said earlier, some types of characters are stronger against other types, which means they'll deal 50% more damages against them.

For example, if a Wizard is attacking a Paladin, it'll deal:

```
total_damage = (attack_damage * attacker_power * 1.5) / defenser_resistance
```

Each attack is removing HPs to the defenser character, and the fight is over when at least one character dies. If one character remains alive, it's the winner of the fight.

## New need! Some additional rules!

It's time to get the benefits of your clean code! Here are some new rules to implement!

Characters now have special abilities depending on their types! They may cast spells which are helping them during their fights!

Wizard have a 20% chance to heal 10% of their HPs each time they attack.

Paladins have a 20% chance to double their resistance each time they defend.


Rogues have a 20% chance to double their damages each time they attack.


-----------------------------------------------------------

# Background #

This is a fun kata that has the programmer building simple combat rules, as for a role-playing game (RPG). It is implemented as a sequence of iterations. The domain doesn't include a map or any other character skills apart from their ability to damage and heal one another.

# Instructions #

1. Complete each iteration before reading the next one.

1. It's recommended you perform this kata with a pairing partner and while writing tests.

## Iteration One ##

1. All Characters, when created, have:
    - Health, starting at 1000
    - Level, starting at 1
    - May be Alive or Dead, starting Alive (Alive may be a true/false)

1. Characters can Deal Damage to Characters.
    - Damage is subtracted from Health
    - When damage received exceeds current Health, Health becomes 0 and the character dies

1. A Character can Heal a Character.
    - Dead characters cannot be healed
    - Healing cannot raise health above 1000

## Iteration Two ##

1. A Character cannot Deal Damage to itself.

1. A Character can only Heal itself.

1. When dealing damage:
    - If the target is 5 or more Levels above the attacker, Damage is reduced by 50%
    - If the target is 5 or more levels below the attacker, Damage is increased by 50%

## Iteration Three ##

1. Characters have an attack Max Range.

1. *Melee* fighters have a range of 2 meters.

1. *Ranged* fighters have a range of 20 meters.

1. Characters must be in range to deal damage to a target.

## Retrospective ##

- Are you keeping up with the requirements? Has any iteration been a big challenge?
- Do you feel good about your design? Is it scalable and easily adapted to new requirements?
- Is everything tested? Are you confident in your code?

## Iteration Four ##

1. Characters may belong to one or more Factions.
    - Newly created Characters belong to no Faction.

1. A Character may Join or Leave one or more Factions.

1. Players belonging to the same Faction are considered Allies.

1. Allies cannot Deal Damage to one another.

1. Allies can Heal one another.

## Iteration Five ##

1. Characters can damage non-character *things* (props).
    - Anything that has Health may be a target
    - These things cannot be Healed and they do not Deal Damage
    - These things do not belong to Factions; they are neutral
    - When reduced to 0 Health, things are *Destroyed*
    - As an example, you may create a Tree with 2000 Health

## Retrospective ##

- What problems did you encounter?
- What have you learned? Any new technique or pattern?
- Share your design with others, and get feedback on different approaches.

# Resources #

- Original Source: http://www.slideshare.net/DanielOjedaLoisel/rpg-combat-kata
