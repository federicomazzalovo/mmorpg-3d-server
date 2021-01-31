using Godot;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;



public enum CharacterClass
{
	Unknown,
	Paladin,
	Wizard,
	Rogue
}


public class Character
{
	public long InitHp { get; set; }
	public long Hp { get; set; }
	public long Id { get; set; }
	public long Level { get; set; }
	public long Resistance { get; set; }
	public CharacterPosition Position { get; set; }
	public bool Alive { get; set; }
	public bool Dead { get; set; }

	public bool IsPlayer { get; set; }

	public CharacterClass CharacterClass { get; set; }
}

public class CharacterPosition
{

	public CharacterPosition(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	public float x { get; set; }
	public float y { get; set; }
}

public class Battlefield : Node2D
{
	private CharacterNode playerNode;
	private CharacterNode genericNodeToClone;

	public void DeselectAllEnemies()
	{
		foreach(Node children in this.GetChildren())
		{
			if(children is NpcNode)
			{
				NpcNode npc = children as NpcNode;
				npc.Deselect();
			}
		}
	}

	private IEnumerable<Character> characters;
	private Character player;

	public override void _Ready()
	{
		this.genericNodeToClone = this.GetNode("NpcNode") as CharacterNode;
		this.playerNode = this.GetNode("PlayerNode") as CharacterNode;

		this.characters = this.RetrieveCharacters();
		this.LoadCharacters();
		this.AddCharactersSprites();		
	}


	private void LoadCharacters()
	{
		this.player = this.characters.SingleOrDefault(character => character.IsPlayer);
		this.characters = this.characters.Where(character => !character.IsPlayer);        
	}

	private void AddCharactersSprites()
	{
		this.playerNode.Initialize(this.player);

		foreach (Character character in this.characters)
			this.AddCharacterSprite(character);
	}

	private void AddCharacterSprite(Character character)
	{
		CharacterNode characterSprite = this.genericNodeToClone.Duplicate() as CharacterNode;
		this.AddChild(characterSprite);

		characterSprite.Initialize(character);

		characterSprite.Show();		
	}

	private List<Character> RetrieveCharacters()
	{
		using (HttpClient client = new HttpClient())
		{
			HttpResponseMessage response = client.GetAsync("http://localhost:8080/api/character/all/alive").Result;

			string json = response.Content.ReadAsStringAsync().Result;

			return JsonConvert.DeserializeObject<List<Character>>(json);
		}
	}

	public CharacterNode GetSelectedTarget()
	{
		foreach (Node children in this.GetChildren())
		{
			if (children is NpcNode)
			{
				NpcNode npc = children as NpcNode;
				if (npc.IsSelected)
					return npc;
			}
		}
		return null;
	}
}
