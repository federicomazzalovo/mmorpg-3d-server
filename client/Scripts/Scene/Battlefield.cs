using Godot;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;

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
}

public class CharacterPosition
{
    public long X { get; set; }
    public long Y { get; set; }
}

public class Battlefield : Node2D
{
    private Sprite spriteToClone;
    private IEnumerable<Character> characters;
    private Character myCharacter;

    public override void _Ready()
    {
        this.spriteToClone = this.GetNode("Sprite") as Sprite;

        this.characters = this.RetrieveCharacters();
        this.LoadCharacters();
        this.AddCharactersSprites();
    }

    public override void _Input(InputEvent @event)
    {
        base._Input(@event);
    }

    private void LoadCharacters()
    {
        this.myCharacter = this.characters.FirstOrDefault();
        this.characters = this.characters.Where(character => character.Id != this.myCharacter.Id);        
    }

    private void AddCharactersSprites()
    {
        foreach (Character character in this.characters)
            this.AddCharacterSprite(character);
    }

    private void AddCharacterSprite(Character character)
    {
        Sprite characterSprite = this.spriteToClone.Duplicate() as Sprite;
        characterSprite.Position = new Vector2(character.Position.X, character.Position.Y);
        characterSprite.Show();

        this.AddChild(characterSprite);
    }

    private List<Character> RetrieveCharacters()
    {
        using (HttpClient client = new HttpClient())
        {
            HttpResponseMessage response = client.GetAsync("http://localhost:8080/api/character/all").Result;

            string json = response.Content.ReadAsStringAsync().Result;

            return JsonConvert.DeserializeObject<List<Character>>(json);
        }
    }
}
