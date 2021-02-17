using Godot;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Simplerpgkataclient.Network
{
    public class WebSocketService : Godot.Object
    {
        private const string WEB_SOCKET_URL = "ws://localhost:8080/";

        private WebSocketClient client;
        private static WebSocketService instance;

        public event EventHandler<object> ConnectionClosedEvent = delegate { };
        public event EventHandler<object> ConnectionEnstablishedEvent = delegate { };
        public event EventHandler<object> DataReceivedEvent = delegate { };

        private WebSocketService()
        {
            this.client = new WebSocketClient();
        }

        public Error Connect(string endpoint)
        {
            this.client.Connect("connection_closed", this, "OnConnectionClosed");
            this.client.Connect("connection_error", this, "OnConnectionClosed");
            this.client.Connect("connection_established", this, "OnConnectedEnstablished");
            this.client.Connect("data_received", this, "OnDataReceived");

            return this.client.ConnectToUrl(WEB_SOCKET_URL + endpoint);
        }

        public static WebSocketService GetInstance()
        {
            if(instance == null)
                instance = new WebSocketService();

            return instance;
        }

        public void Poll()
        {
            this.client.Poll();
        }

        public void SendMessage(string message)
        {
            this.client.GetPeer(1).SetWriteMode(WebSocketPeer.WriteMode.Text);
            this.client.GetPeer(1).PutPacket(message.ToUTF8());
        }

        private void OnConnectionClosed(bool isClose)
        {
            this.ConnectionClosedEvent(this, "aaa");
        }

        private void OnConnectedEnstablished(string proto = "")
        {
            this.ConnectionEnstablishedEvent(this, "aaa");
        }

        private void OnDataReceived()
        {
            this.DataReceivedEvent(this, "aaa");
        }
    }
}
