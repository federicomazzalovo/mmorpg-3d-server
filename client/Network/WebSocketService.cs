using Godot;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Simplerpgkataclient.Network
{
    public class WebSocketService : Godot.Object, IObservable<Node> 
    {
        private const string WEB_SOCKET_URL = "ws://localhost:8080/";

        private WebSocketClient client;
        private static WebSocketService instance;

        private List<IObserver<Node>> observers;
 
        private WebSocketService()
        {
            this.client = new WebSocketClient();
            this.observers = new List<IObserver<Node>>();
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

        private void OnConnectionClosed(bool isClose)
        {

        }

        private void OnConnectedEnstablished(string proto = "")
        {

        }

        private void OnDataReceived()
        {
        
            foreach(var observer in this.observers){
                observer.OnNext(new Node());
            }
            
        }

        public IDisposable Subscribe(IObserver<Node> observer)
        {
            if(!this.observers.Contains(observer))
                this.observers.Add(observer);
 
            return new Subscription(observers, observer);
        }


    }
}
