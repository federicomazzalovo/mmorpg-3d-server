using Godot;
using Newtonsoft.Json;
using Simplerpgkataclient.Network;
using System;
using System.Net.Http;

public class PlayerNode : CharacterNode
{
	const int walkSpeed = 200;
	Vector2 velocity;

	public int KEY_SPACE = 32;
	private bool attackStarted;
	private int counter = 0;

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

	public override void _PhysicsProcess(float delta)
	{
		base._PhysicsProcess(delta);

		if (this.playerSprite == null)
			this.playerSprite = this.GetNode("/root/Battlefield/PlayerNode/CharacterSprite") as AnimatedSprite;

		this.HandlePlayerAction();

		this.handleAttackAction();

		if (isNavigationButtonReleased())
			this.UpdatePosition(this.Position.x, this.Position.y);

		// We don't need to multiply velocity by delta because "MoveAndSlide" already takes delta time into account.

		// The second parameter of "MoveAndSlide" is the normal pointing up.
		// In the case of a 2D platformer, in Godot, upward is negative y, which translates to -1 as a normal.
		MoveAndSlide(velocity, new Vector2(0, -1));
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
		var x = 1;
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
		}
		else if (Input.IsActionPressed("ui_down"))
		{
			playerSprite.Play("walk_down_facing");
			velocity.y = walkSpeed;
		}
		else if(Input.IsActionPressed("ui_left"))
		{
			playerSprite.FlipH = false;
			playerSprite.Play("walk_side_facing");
			velocity.x = -walkSpeed;
		}
		else if(Input.IsActionPressed("ui_right"))
		{
			playerSprite.FlipH = true;
			playerSprite.Play("walk_side_facing");
			velocity.x = walkSpeed;
		}
		else if(Input.IsKeyPressed(KEY_SPACE))
		{
			this.attackStarted = true;			
		}
		else if(this.attackStarted)
		{
			playerSprite.Play("attack");
		}
		else 
		{
			playerSprite.Play("idle");
			velocity.x = 0;
			velocity.y = 0;
		}

	}

	private void UpdatePosition(float x, float y)
	{
		string message = JsonConvert.SerializeObject(new WebSocketParams() { characterId = this.Character.Id, positionX = x, positionY = y });
		WebSocketService.GetInstance().SendMessage(message);
	}

	private void AttackEnemy()
	{
		using (HttpClient client = new HttpClient())
		{
			CharacterNode selectedTarget = this.ParentNode.GetSelectedTarget();
			if (selectedTarget != null && !selectedTarget.Character.Dead)
			{
				long targetId = selectedTarget.Character.Id;
				HttpResponseMessage response = client.GetAsync("http://localhost:8080/api/character/" + this.Character.Id + "/attack/" + targetId).Result;
				string json = response.Content.ReadAsStringAsync().Result;

				selectedTarget.UpdateCharacter();
			}
		}
	}

	private bool isNavigationButtonReleased()
	{
		return Input.IsActionJustReleased("ui_left")
			|| Input.IsActionJustReleased("ui_right")
			|| Input.IsActionJustReleased("ui_up")
			|| Input.IsActionJustReleased("ui_down");
	}

	private void _on_PlayerSprite_animation_finished()
	{
		this.attackStarted = false;
	}
}
