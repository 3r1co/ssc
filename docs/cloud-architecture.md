# Cloud Architecture and Migration Exercise (30 Minutes)

Your Business Line has increasing demand from different goverments for a SaaS-based solution in order to easily manage digital documents.  
You want to leverage on the public cloud, but certain constraints need to be met: 

* One component of your solution is using a HSM (Hardware Security Module) to encrypt PII (Personally Identifiable Information). This component must stay hosted in a Private Datacenter (1)
* One component is in maintenance mode since several years, but you'd like to reduce the hardware cost in your datacenter. It should therefore be migrated to the public cloud in the most cost efficient way. (2)
* One component is under active development, however, it is used in your solution, but also for a solution from another vertical which is deploying their solution also in-house at their customers. Therefore, it can  not be locked in too far with a specific cloud provider (3)
* One component is completely owned by your team and it will not be used by other verticals. It's processing data 24/7 with a very steady workload. (4)
* One component is completely owned by your team and it will not be used by other verticals. It's sporadically used when a certain event occurs (5)

Draw a diagram of a possible solution architecture, taking all the above constraints into account.  
Gather in teams of five persons and present your diagram at the end on a whiteboard.