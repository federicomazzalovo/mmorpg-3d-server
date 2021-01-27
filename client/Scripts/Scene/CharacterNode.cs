using Godot;
using Newtonsoft.Json;
using System;
using System.Net.Http;

public abstract class CharacterNode : KinematicBody2D
{

	public Character character { get; private set; }
	public Label HpLabel { get; private set; }

	public override void _Ready()
	{
		base._Ready();

		this.HpLabel = this.GetNode("Hp") as Label;
	}

	public void Initialize(Character character)
    {
        this.character = character;
        this.RenderCharacter();
    }

    private void RenderCharacter()
    {
        this.Position = new Vector2(character.Position.x, character.Position.y);
        this.HpLabel.Text = character.Hp.ToString();
    }

    public void UpdateCharacter()
    {
		using (HttpClient client = new HttpClient())
		{
			HttpResponseMessage response = client.GetAsync("http://localhost:8080/api/character/" + this.character.Id).Result;
			string json = response.Content.ReadAsStringAsync().Result;

			this.Initialize(JsonConvert.DeserializeObject<Character>(json));
		}
	}
}
