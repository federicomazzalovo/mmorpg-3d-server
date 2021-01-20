using Godot;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
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
}

public class CharacterPosition
{
	public long X { get; set; }
	public long Y { get; set; }
}

public class Battlefield : Node2D
{
	public override void _Ready()
	{
		using (HttpClient client = new HttpClient())
		{
			HttpResponseMessage response = client.GetAsync("http://localhost:8080/api/character/all").Result;

			string result = response.Content.ReadAsStringAsync().Result;

			List<Character> characters = JsonConvert.DeserializeObject<List<Character>>(result);

			Sprite sprite = this.GetNode("Sprite") as Sprite;

			foreach (Character character in characters)
			{
				Sprite characterSprite = sprite.Duplicate() as Sprite;
				characterSprite.Position = new Vector2(character.Position.X, character.Position.Y);
				characterSprite.Show();

				this.AddChild(characterSprite);
			}
		}
	}
}
