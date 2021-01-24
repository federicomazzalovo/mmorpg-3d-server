using Godot;
using System;

public class NpcNode : CharacterNode
{
	private bool _selected;
	public bool IsSelected
	{
		get
		{
			return this._selected;
		}
		set
		{
			if (value)
				this.SelectedBorder.Show();
			else
				this.SelectedBorder.Hide();

			this._selected = value;
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

	public void Deselect()
	{
		this.IsSelected = false;
	}

	public void _on_SelectedButton_pressed()
	{
		this.ParentNode.DeselectAllEnemies();
		this.IsSelected = true;
	}
}
