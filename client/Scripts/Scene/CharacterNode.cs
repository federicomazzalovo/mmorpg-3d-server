using Godot;
using Newtonsoft.Json;
using System;
using System.Net;
using System.Net.Http;

public abstract class CharacterNode : KinematicBody2D
{
	private AnimatedSprite animatedSprite;

	public Character Character { get; private set; }
	public Label HpLabel { get; private set; }

	public override void _Ready()
	{
		base._Ready();

		this.HpLabel = this.GetNode("Hp") as Label;
	}

	public void Initialize(Character character)
	{
		this.Character = character;
		this.RenderCharacter();
		this.CreatePlayerSprite();
	}

	private void CreatePlayerSprite()
	{
		AnimatedSprite animatedSprite = null;

		switch (Character.CharacterClass)
		{
			case CharacterClass.Paladin:

				animatedSprite = this.GetNode("/root/Battlefield/WarriorSprite") as AnimatedSprite;
				break;

			case CharacterClass.Rogue:

				animatedSprite = this.GetNode("/root/Battlefield/RogueSprite") as AnimatedSprite;
				break;

			case CharacterClass.Wizard:

				animatedSprite = this.GetNode("/root/Battlefield/WizardSprite") as AnimatedSprite;
				break;
		}

		//	this.GetParent().RemoveChild(animatedSprite);
		this.animatedSprite = animatedSprite.Duplicate() as AnimatedSprite;
		this.animatedSprite.Name = "CharacterSprite";
		this.animatedSprite.Show();
		this.AddChild(this.animatedSprite);
	}

	private void RenderCharacter()
    {
        this.Position = new Vector2(Character.Position.x, Character.Position.y);
        this.RenderLifeStatus();
    }

    public void RenderLifeStatus()
    {
        this.HpLabel.Text = Character.Hp.ToString();

        if (this.Character.Dead)
            this.Kill();
    }

    private void Kill()
	{
		this.animatedSprite.Play("death");
		this.SetPhysicsProcess(false);
	}

	public void UpdateCharacter()
	{
		using (HttpClient client = new HttpClient())
		{
			HttpResponseMessage response = client.GetAsync("http://simple-rpg-kata.herokuapp.com/api/character/" + this.Character.Id).Result;
			string json = response.Content.ReadAsStringAsync().Result;

			Character character = JsonConvert.DeserializeObject<Character>(json);

			this.Character = character;
			this.RenderCharacter();
		}
	}
}
