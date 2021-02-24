using Godot;
using Newtonsoft.Json;
using Simplerpgkataclient.Network;
using Simplerpgkataclient.Scripts.Scene;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;

public enum CharacterClass
{
	Unknown,
	Paladin,
	Wizard,
	Rogue
}


public class Character
{
	public long InitHp { get; set; }
	public long Hp { get; set; }
	public long Id { get; set; }
	public long Level { get; set; }
	public long Resistance { get; set; }
	public CharacterPosition Position { get; set; }
	public bool Alive { get; set; }
	public bool Dead { get; set; }

	public bool IsPlayer { get; set; }

	public CharacterClass CharacterClass { get; set; }
}

public class CharacterPosition
{

	public CharacterPosition(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	public float x { get; set; }
	public float y { get; set; }
}

public class Battlefield : Node2D
{
	private CharacterNode playerNode;
	// List of character exclude the current player
	private List<CharacterNode> characterNodes = new List<CharacterNode>();
	
	private CharacterNode genericNodeToClone;
	private IEnumerable<Character> characters;
	private Character player;

	public override void _Ready()
	{
 
		this.genericNodeToClone = this.GetNode("NpcNode") as CharacterNode;
		this.playerNode = this.GetNode("PlayerNode") as CharacterNode;

		this.characters = this.RetrieveCharacters();
		this.LoadCharacters();
		this.AddCharactersSprites();

		WebSocketService webSocketService = WebSocketService.GetInstance();
		var err = webSocketService.Connect("my-websocket-endpoint");

		if (err != Error.Ok)
		{
			GD.Print("Unable to connect");
			this.SetProcess(false);
		}			
		else
		{
			webSocketService.ConnectionClosedEvent += this.ConnectionClosed;
			webSocketService.ConnectionEnstablishedEvent += this.ConnectionEnstablished;
			webSocketService.DataReceivedEvent += this.DataChanged;
		}
			
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
		// For now  this method only receive a list of character position
		string message = e as string;
		List<WebSocketParams> webSocketParamsList =  JsonConvert.DeserializeObject<List<WebSocketParams>>(message);

		foreach(var param in webSocketParamsList)
		{
			CharacterNode node = this.characterNodes.FirstOrDefault(c => c.Character.Id == param.characterId);
			if(node != null)
			{
				node.Character.Position = new CharacterPosition(param.positionX, param.positionY);
				// Update the UI 
				node.Position = new Vector2(param.positionX, param.positionY);
			}
		}

		GD.Print(e);
	}

	public override void _Process(float delta)
	{
		base._Process(delta);
		WebSocketService.GetInstance().Poll();
	}

	private void LoadCharacters()
	{
		this.player = this.characters.SingleOrDefault(character => character.IsPlayer);
		this.characters = this.characters.Where(character => !character.IsPlayer);        
	}

	private void AddCharactersSprites()
	{
		this.playerNode.Initialize(this.player);

		foreach (Character character in this.characters)
			this.AddCharacterSprite(character);
	}

	private void AddCharacterSprite(Character character)
	{
		CharacterNode characterSprite = this.genericNodeToClone.Duplicate() as CharacterNode;
		this.AddChild(characterSprite);

		characterSprite.Initialize(character);

		characterSprite.Show();

		this.characterNodes.Add(characterSprite);
	}

	private List<Character> RetrieveCharacters()
	{
		using (HttpClient client = new HttpClient())
		{
			HttpResponseMessage response = client.GetAsync("http://localhost:8080/api/character/all/alive").Result;

			string json = response.Content.ReadAsStringAsync().Result;

			return JsonConvert.DeserializeObject<List<Character>>(json);
		}
	}

	public CharacterNode GetSelectedTarget()
	{
		foreach (Node children in this.GetChildren())
		{
			if (children is NpcNode)
			{
				NpcNode npc = children as NpcNode;
				if (npc.IsSelected)
					return npc;
			}
		}
		return null;
	}


	public void DeselectAllEnemies()
	{
		foreach (Node children in this.GetChildren())
		{
			if (children is NpcNode)
			{
				NpcNode npc = children as NpcNode;
				npc.Deselect();
			}
		}
	}

	public void OnNext(Node value)
	{
		GD.Print(value);
   //     throw new NotImplementedException();
	}

	public void OnError(Exception error)
	{
		GD.Print(error);
		//   throw new NotImplementedException();
	}

	public void OnCompleted()
	{
		GD.Print("on completed");
		// throw new NotImplementedException();
	}
}
