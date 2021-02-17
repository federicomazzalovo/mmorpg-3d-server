using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Simplerpgkataclient.Network
{
    public class Subscription : IDisposable
    {
        private List<IObserver<Godot.Node>> Observers;
        private IObserver<Godot.Node> Observer;

        public Subscription(List<IObserver<Godot.Node>> observers, IObserver<Godot.Node> observer)
        {
            this.Observers = observers;
            this.Observer = observer;
        }

        public void Dispose()
        {
            if (this.Observer != null && this.Observers.Contains(this.Observer))
                this.Observers.Remove(this.Observer);
        }
    }
}
