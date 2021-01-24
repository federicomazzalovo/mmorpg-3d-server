using Godot;
using Newtonsoft.Json;
using System;
using System.Net.Http;

public class PlayerNode : CharacterNode
{
	const int walkSpeed = 200;
	Vector2 velocity;

	public override void _PhysicsProcess(float delta)
	{
		base._PhysicsProcess(delta);


		if (Input.IsActionPressed("ui_left"))
		{
			velocity.x = -walkSpeed;
		}
		else if (Input.IsActionPressed("ui_right"))
		{
			velocity.x = walkSpeed;
		}
		else if (Input.IsActionPressed("ui_up"))
		{
			velocity.y = -walkSpeed;
		}
		else if (Input.IsActionPressed("ui_down"))
		{
			velocity.y = walkSpeed;

		}
		else
		{
			velocity.x = 0;
			velocity.y = 0;
		}

		if(isNavigationButtonReleased())
        {
			this.UpdatePosition(this.GetPosition().x, this.GetPosition().y);
        }

		// We don't need to multiply velocity by delta because "MoveAndSlide" already takes delta time into account.

		// The second parameter of "MoveAndSlide" is the normal pointing up.
		// In the case of a 2D platformer, in Godot, upward is negative y, which translates to -1 as a normal.
		MoveAndSlide(velocity, new Vector2(0, -1));
	}

    private void UpdatePosition(float x, float y)
    {
		using (HttpClient client = new HttpClient())
		{
			CharacterPosition newPosition = new CharacterPosition(x, y);
			this.character.Position = newPosition;
			HttpContent httpContent = new StringContent(JsonConvert.SerializeObject(newPosition));
			httpContent.Headers.ContentType = new System.Net.Http.Headers.MediaTypeHeaderValue("application/json");
			HttpResponseMessage response = client.PutAsync("http://localhost:8080/api/character/"+ this.character.Id + "/position", httpContent).Result;

			string json = response.Content.ReadAsStringAsync().Result;
		}
	}

    private bool isNavigationButtonReleased()
    {
		return Input.IsActionJustReleased("ui_left")
			|| Input.IsActionJustReleased("ui_right")
			|| Input.IsActionJustReleased("ui_up")
			|| Input.IsActionJustReleased("ui_down");
	}
}
