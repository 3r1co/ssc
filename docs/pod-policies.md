# Kubernetes Network Policies (1 hour)

As you saw throughout the course, Kubernetes separates projects through the notion of __namespaces__.

By default, Kubernetes enables Pods to communicate between namespaces, which goes against of the security principles that you learned about earlier: Least privilege. As a reminder, it is a security best practice to only allow the absolute necessary.

There is a very good Github project visualizing the different network policy configurations [here](https://github.com/ahmetb/kubernetes-network-policy-recipes).

Your exercise is the apply the different configurations and test them accordingly. It should give you a good overview on how to apply Kubernetes network policies in real life.
