using Godot;
using Newtonsoft.Json;
using Simplerpgkataclient.Network;
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

public class Battlefield : Node2D, IObserver<Node>
{
	private CharacterNode playerNode;
	private CharacterNode genericNodeToClone;


	// The URL we will connect to
	private string websocketUrl = "ws://localhost:8080/my-websocket-endpoint";

	// Our WebSocketClient instance
	WebSocketClient _client = new WebSocketClient();


	private IEnumerable<Character> characters;
	private Character player;

	public override void _Ready()
	{
		this.genericNodeToClone = this.GetNode("NpcNode") as CharacterNode;
		this.playerNode = this.GetNode("PlayerNode") as CharacterNode;

		this.characters = this.RetrieveCharacters();
		this.LoadCharacters();
		this.AddCharactersSprites();


		_client.Connect("connection_closed", this, "_closed");
		_client.Connect("connection_error", this, "_closed");
		_client.Connect("connection_established", this, "_connected");
		_client.Connect("data_received", this, "_on_data");

		var err = _client.ConnectToUrl(this.websocketUrl);
		
		if(err != Error.Ok)
		{
			Console.WriteLine("Unable to connect");
			SetProcess(false);
		}


		WebSocketService webSocketService = WebSocketService.GetInstance();
		webSocketService.Connect("");


		webSocketService.Subscribe(this);

	}

	public override void _Process(float delta)
	{
		base._Process(delta);
		_client.Poll();
	}

	private void _closed(bool isClose)
	{
		Console.WriteLine("Closed, ");
		SetProcess(false);
	}


	private void _connected(string proto ="")
	{
		_client.GetPeer(1).SetWriteMode(WebSocketPeer.WriteMode.Text);
		_client.GetPeer(1).PutPacket("Test packet".ToUTF8());
	}

	private void _on_data()
	{
		_client.GetPeer(1).SetWriteMode(WebSocketPeer.WriteMode.Text);
		_client.GetPeer(1).AllowObjectDecoding = true;
		byte[] bytes = _client.GetPeer(1).GetPacket();
		string result = Encoding.UTF8.GetString(bytes, 0, bytes.Length);

		GD.Print("Data from server " + result);
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
