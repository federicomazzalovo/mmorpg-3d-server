using Godot;
using System;

public class NpcNode : CharacterNode
{
	private bool selected;
	public bool SetSelected
	{
		get
		{
			return this.selected;
		}
		set
		{
			if (value)
				this.SelectedBorder.Show();
			else
				this.SelectedBorder.Hide();

			this.selected = value;
		}
	}

	public Battlefield ParentNode { get; private set; }
	public ColorRect SelectedBorder { get; private set; }

	public override void _Ready()
	{
		base._Ready();

		this.ParentNode = this.GetNode("/root/Battlefield") as Battlefield;
		this.SelectedBorder = this.GetNode("SelectedBorder") as ColorRect;
	}

	public override void _Input(InputEvent @event)
	{
		base._Input(@event);

		if (Input.IsMouseButtonPressed((int)ButtonList.Left))
		{
			this.ParentNode.DeselectAllEnemies();
			this.SetSelected = true;
		}

	}

	public void Deselect()
	{
		this.SetSelected = false;
	}
}
