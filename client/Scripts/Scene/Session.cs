using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Simplerpgkataclient.Scripts.Scene
{
    public static class Session
    {
        public static string Username { get; set; }
        public static int ClassId { get; internal set; }
        public static string WSSessionId { get; internal set; }
    }
}
