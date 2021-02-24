using Godot;
using Simplerpgkataclient.Scripts.Scene;
using System;

public class Login : Control
{
 

	// Called when the node enters the scene tree for the first time.
	public override void _Ready()
	{
		
	}

	//  // Called every frame. 'delta' is the elapsed time since the previous frame.
	//  public override void _Process(float delta)
	//  {
	//      
	//  }

	private void _on_Button_pressed()
	{
		LineEdit usernameInput = this.GetNode("usernameInput") as LineEdit;
		string usernameValue = usernameInput.Text;
		if (String.IsNullOrWhiteSpace(usernameValue))
			return;

		Session.Username = usernameValue;
		this.GetTree().ChangeScene("res://Scene/Battlefield.tscn");
	}

}


