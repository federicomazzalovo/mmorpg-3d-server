using Godot;
using Simplerpgkataclient.Network;

public class NpcNode : CharacterNode
{
	const int walkSpeed = 200;
	Vector2 velocity = new Vector2(0, 0);

	private AnimatedSprite npcSprite { get; set; }

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

	public override void _PhysicsProcess(float delta)
	{
		base._PhysicsProcess(delta);

		MoveAndSlide(this.velocity, new Vector2(0, -1));
	}

	public void UpdateSprite(MoveDirection moveDirection)
	{
		if (this.npcSprite == null)
			this.npcSprite = this.GetNode("CharacterSprite") as AnimatedSprite;

		switch (moveDirection)
		{

			case MoveDirection.Up:
				velocity.y = -walkSpeed;
				this.npcSprite.Play("walk_up_facing");
				break;
			case MoveDirection.Right:
				velocity.x = walkSpeed;
				this.npcSprite.FlipH = true;
				this.npcSprite.Play("walk_side_facing");
				break;
			case MoveDirection.Down:
				velocity.y = walkSpeed;
				this.npcSprite.Play("walk_down_facing");
				break;
			case MoveDirection.Left:
				velocity.x = -walkSpeed;
				this.npcSprite.FlipH = false;
				this.npcSprite.Play("walk_side_facing");
				break;
			case MoveDirection.None:
			default:
				this.npcSprite.Play("idle");
				velocity.x = 0;
				velocity.y = 0;
				break;
		}		
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

	public override void UpdateCharacter(WebSocketParams param)
	{
		base.UpdateCharacter(param);

		MoveDirection moveDirection = (MoveDirection)param.moveDirection;
		this.UpdateSprite(moveDirection);
	}
}
