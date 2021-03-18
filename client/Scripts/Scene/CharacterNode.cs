using Godot;
using Newtonsoft.Json;
using Simplerpgkataclient.Network;
using System;
using System.Net;
using System.Net.Http;

public abstract class CharacterNode : KinematicBody2D
{
    private AnimatedSprite animatedSprite;

    public Character Character { get; private set; }
    public Label HpLabel { get; private set; }



    public override void _Ready()
    {
        base._Ready();

        this.HpLabel = this.GetNode("Hp") as Label;

    }

    public void Initialize(Character character)
    {
        this.Character = character;
        this.RenderCharacter();
        this.CreatePlayerSprite();
    }

    private void CreatePlayerSprite()
    {
        AnimatedSprite animatedSprite = null;

        switch (Character.CharacterClass)
        {
            case CharacterClass.Paladin:

                animatedSprite = this.GetNode("/root/Battlefield/WarriorSprite") as AnimatedSprite;
                break;

            case CharacterClass.Rogue:

                animatedSprite = this.GetNode("/root/Battlefield/RogueSprite") as AnimatedSprite;
                break;

            case CharacterClass.Wizard:

                animatedSprite = this.GetNode("/root/Battlefield/WizardSprite") as AnimatedSprite;
                break;
        }

        //	this.GetParent().RemoveChild(animatedSprite);
        this.animatedSprite = animatedSprite.Duplicate() as AnimatedSprite;
        this.animatedSprite.Name = "CharacterSprite";
        this.animatedSprite.Show();
        this.AddChild(this.animatedSprite);
    }

    private void RenderCharacter()
    {
        this.Position = new Vector2(Character.Position.x, Character.Position.y);
        this.RenderLifeStatus();
    }

    public void RenderLifeStatus()
    {
        this.HpLabel.Text = Character.Hp.ToString();
    }



    private void Kill()
    {
        this.animatedSprite.Play("death");
        this.SetPhysicsProcess(false);
        this.Killed();
    }


    protected virtual void Killed() { }

    public virtual void UpdateCharacter(WebSocketParams param)
    {
        if (this.Character.Hp > 0 && param.hp == 0)
            this.Kill();

        this.Character.Position = new CharacterPosition(param.positionX, param.positionY);
        this.Character.Hp = param.hp;

        this.RenderLifeStatus();

        MoveDirection moveDirection = (MoveDirection)param.moveDirection;
        if (moveDirection == MoveDirection.None)
            this.Position = new Vector2(param.positionX, param.positionY);
    }
}
