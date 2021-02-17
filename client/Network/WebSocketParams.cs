using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Simplerpgkataclient.Network
{
    public class WebSocketParams
    {
        public long characterId { get; set; }
        public float positionX { get; set; } 
        public float positionY { get; set; }
    }
}
