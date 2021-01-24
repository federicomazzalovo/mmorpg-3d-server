using Godot;
using System;

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
		this.Position = new Vector2(character.Position.x, character.Position.y);
		this.HpLabel.Text = character.Hp.ToString();
	}
}
