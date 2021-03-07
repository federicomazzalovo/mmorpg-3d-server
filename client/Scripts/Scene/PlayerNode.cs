using Godot;
using Newtonsoft.Json;
using Simplerpgkataclient.Network;
using System;
using System.Net;
using System.Net.Http;

public class PlayerNode : CharacterNode
{
	const int walkSpeed = 200;
	private Vector2 velocity;
	private MoveDirection moveDirection = MoveDirection.None;

	public int KEY_SPACE = 32;
	private bool attackStarted;

	public Battlefield ParentNode { get; private set; }

	private AnimatedSprite playerSprite { get; set; }

	public override void _Ready()
	{
		base._Ready();
		this.ParentNode = this.GetNode("/root/Battlefield") as Battlefield;

		WebSocketService webSocketService = WebSocketService.GetInstance();

		webSocketService.ConnectionClosedEvent += this.ConnectionClosed;
		webSocketService.ConnectionEnstablishedEvent += this.ConnectionEnstablished;
		webSocketService.DataReceivedEvent += this.DataChanged;
	}

	private bool updatePositionRequired = true;
	public override void _PhysicsProcess(float delta)
	{
		base._PhysicsProcess(delta);

		if (this.playerSprite == null)
			this.playerSprite = this.GetNode("/root/Battlefield/PlayerNode/CharacterSprite") as AnimatedSprite;

		this.HandlePlayerAction();

		this.handleAttackAction();

		if (this.updatePositionRequired)
			this.UpdatePosition(this.Position.x, this.Position.y);

		this.updatePositionRequired = this.velocity.x != 0 || this.velocity.y != 0;

		// We don't need to multiply velocity by delta because "MoveAndSlide" already takes delta time into account.

		// The second parameter of "MoveAndSlide" is the normal pointing up.
		// In the case of a 2D platformer, in Godot, upward is negative y, which translates to -1 as a normal.
		MoveAndSlide(this.velocity, new Vector2(0, -1));
	}



	private void ConnectionEnstablished(object sender, object e)
	{
		GD.Print("Connection enstablished");
	}

	private void ConnectionClosed(object sender, object e)
	{
		GD.Print("Connection closed");
	}

	private void DataChanged(object sender, object e)
	{
	}

	public override void _Process(float delta)
	{
		base._Process(delta);
		WebSocketService.GetInstance().Poll();
	}


	private void handleAttackAction()
	{
		if (!Input.IsKeyPressed(KEY_SPACE))
			return;

		this.AttackEnemy();
	}

	private void HandlePlayerAction()
	{
		if (Input.IsActionPressed("ui_up"))
		{
			playerSprite.Play("walk_up_facing");
			velocity.y = -walkSpeed;
			this.moveDirection = MoveDirection.Up;
		}
		else if (Input.IsActionPressed("ui_down"))
		{
			playerSprite.Play("walk_down_facing");
			velocity.y = walkSpeed;
			this.moveDirection = MoveDirection.Down;
		}
		else if (Input.IsActionPressed("ui_left"))
		{
			playerSprite.FlipH = false;
			playerSprite.Play("walk_side_facing");
			velocity.x = -walkSpeed;
			this.moveDirection = MoveDirection.Left;
		}
		else if (Input.IsActionPressed("ui_right"))
		{
			playerSprite.FlipH = true;
			playerSprite.Play("walk_side_facing");
			velocity.x = walkSpeed;
			this.moveDirection = MoveDirection.Right;
		}
		else if (Input.IsKeyPressed(KEY_SPACE))
		{
			this.attackStarted = true;
		}
		else if (this.attackStarted)
		{
			playerSprite.Play("attack");
		}
		else
		{
			playerSprite.Play("idle");
			velocity.x = 0;
			velocity.y = 0;
			this.moveDirection = MoveDirection.None;
		}

	}

	private void UpdatePosition(float x, float y)
	{
		string message = JsonConvert.SerializeObject(new WebSocketParams() { characterId = this.Character.Id, positionX = x, positionY = y, moveDirection = (int)this.moveDirection, actionType = (int)ActionType.Movement });
		WebSocketService.GetInstance().SendMessage(message);
	}

	private void AttackEnemy()
	{
		CharacterNode selectedTarget = this.ParentNode.GetSelectedTarget();
		int targetId = selectedTarget.Character.Id;

		string message = JsonConvert.SerializeObject(new WebSocketParams() { characterId = this.Character.Id, targetId = targetId, actionType = (int)ActionType.Attack });
		WebSocketService.GetInstance().SendMessage(message);
	}

	private void _on_PlayerSprite_animation_finished()
	{
		this.attackStarted = false;
	}
}
