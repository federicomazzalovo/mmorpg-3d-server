using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Simplerpgkataclient.Network
{
    public enum MoveDirection
    {
        None = 0,
        Up = 1,
        Right = 2,
        Down = 3,
        Left = 4
    }

    public enum ActionType
    {
        None = 0,
        Movement = 1,
        Attack = 2
    }

    public class WebSocketParams
    {
        public long characterId { get; set; }
        public float positionX { get; set; } 
        public float positionY { get; set; }
        public int moveDirection { get; set; }
        public double hp { get; set; }
        public int targetId { get; set; }
        public int actionType { get; set; }
    }
}
