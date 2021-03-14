using Godot;
using Newtonsoft.Json;
using Simplerpgkataclient.Scripts.Scene;
using System;
using System.Net.Http;
using System.Text;

public class Login : Control
{
	private OptionButton classesDropDownList;

	// Called when the node enters the scene tree for the first time.
	public override void _Ready()
	{
		this.classesDropDownList = this.GetNode("VBoxContainer/ClassesDropDownList") as OptionButton;
		classesDropDownList.AddItem(CharacterClass.Paladin.ToString());
		classesDropDownList.AddItem(CharacterClass.Rogue.ToString());
		classesDropDownList.AddItem(CharacterClass.Wizard.ToString());
	}

	//  // Called every frame. 'delta' is the elapsed time since the previous frame.
	//  public override void _Process(float delta)
	//  {
	//      
	//  }

	private void _on_Button_pressed()
	{
		LineEdit usernameInput = this.GetNode("VBoxContainer/UsernameInput") as LineEdit;
		string usernameValue = usernameInput.Text;
		if (String.IsNullOrWhiteSpace(usernameValue))
			return;

		int selectedClassId = this.classesDropDownList.Selected + 1;
		if (selectedClassId < 1 || selectedClassId > 3)
			return;

		Session.Username = usernameValue;
		Session.ClassId = selectedClassId;

		var values = new { username = usernameValue, characterClass = selectedClassId };

		var content = new StringContent(JsonConvert.SerializeObject(values), Encoding.UTF8, "application/json");

		using (HttpClient client = new HttpClient())
		{
			HttpResponseMessage response = client.PostAsync("http://localhost:8080/api/user/login", content).Result;

			if (response.IsSuccessStatusCode)
				this.GetTree().ChangeScene("res://Scene/Battlefield.tscn");
		}
	}

}


