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

    public class WebSocketParams
    {
        public long characterId { get; set; }
        public float positionX { get; set; } 
        public float positionY { get; set; }
        public int moveDirection { get; set; }
        public double hp { get; set; }
        public bool Dead { get; set; }
    }
}
