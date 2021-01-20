using Godot;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;

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
}

public class CharacterPosition
{
	public long X { get; set; }
	public long Y { get; set; }
}

public class Battlefield : Node2D
{
	private KinematicBody2D playerNode;
	private KinematicBody2D genericNodeToClone;
	private IEnumerable<Character> characters;
	private Character player;

	public override void _Ready()
	{
		this.genericNodeToClone = this.GetNode("CharacterNode") as KinematicBody2D;
		this.playerNode = this.GetNode("PlayerNode") as KinematicBody2D;

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
		this.playerNode.Position = new Vector2(this.player.Position.X, this.player.Position.Y);
		foreach (Character character in this.characters)
			this.AddCharacterSprite(character);
	}

	private void AddCharacterSprite(Character character)
	{
		KinematicBody2D characterSprite = this.genericNodeToClone.Duplicate() as KinematicBody2D;
		characterSprite.Position = new Vector2(character.Position.X, character.Position.Y);
		characterSprite.Show();

		this.AddChild(characterSprite);
	}

	private List<Character> RetrieveCharacters()
	{
		using (HttpClient client = new HttpClient())
		{
			HttpResponseMessage response = client.GetAsync("http://localhost:8080/api/character/all").Result;

			string json = response.Content.ReadAsStringAsync().Result;

			return JsonConvert.DeserializeObject<List<Character>>(json);
		}
	}
}
