import java.util.*;

public class RoutingMapTree{

	Exchange root;

	public RoutingMapTree() {
		root = new Exchange(0);
		root.isRoot = true;
		root.parent = null;
	}

	public RoutingMapTree(Exchange r){
		this.root = r;
	}

	public Boolean containsNode(Exchange a){
		if(root == a){
			return true;
		}
		for(int j=0;j<root.numChildren();j++){
			Exchange subroot = root.child(j);
			RoutingMapTree subTree = new RoutingMapTree(subroot);
			if(subTree.containsNode(a)) return true;
		}
		return false;
	}

	public void switchOn(MobilePhone a, Exchange b){
		if(a != null && b != null){
			if(a.status == false){
				a.switchOn();
				if(a.location == null){
					a.location = b;
					while(b.isRoot() == false){
						b.residentSet.myset.Insert(a);
						b = b.parent;
					}
					b.residentSet.myset.Insert(a);
				}
				else{
					Exchange sub = a.location;
					while(sub.isRoot() == false){
						sub.residentSet.myset.Insert(a);
						sub = sub.parent;
					}
					sub.residentSet.myset.Insert(a);
				}
			} else {
				System.out.println("mobile phone is already on");
			}
		} else {
			System.out.println("no such mobile phone");
		}
		return;
	}

	public void switchOff(MobilePhone a){
		if(a.status){
			try{
				Exchange l = a.location();
				while(l.isRoot() == false){
					l = l.parent();
				}
				MobilePhoneSet n = l.residentSet();
				Node w = n.myset.lis.head;
				while(w != null){
					if(((MobilePhone)w.data).id==a.id) break;
					else w = w.next;
				}
				if(w==null)
				{
					System.out.println("no such mobile phone");
					return;
				}
				((MobilePhone)w.data).switchOff();
			} catch(Exception e){
				e.printStackTrace();
			}
		} else {
			System.out.println("mobile phone is already off");
		}
	}

	public Exchange getExchange(int i){
		Exchange temp;
		if(this.root.id == i){
			return this.root;
		}
		for(int k=0;k<root.numChildren();k++){
			Exchange subroot = root.child(k);
			RoutingMapTree subTree = new RoutingMapTree(subroot);
			temp = subTree.getExchange(i);
			if(temp != null){
				return temp;
			}
		}
		return null;
	}

	public MobilePhone findMobilePhone(int x){
		MobilePhoneSet n = root.residentSet();
		Node w = n.myset.lis.head;
		while(w != null){
			if(((MobilePhone)w.data).id == x) break;
			else w = w.next;
		}
		if(w==null)
		{
			//System.out.println("Error - No mobile phone with identifier " + x + " found in the network");
			return null;
		}
		else return ((MobilePhone)w.data);
	}

	public Exchange findMobile(MobilePhone m){
		MobilePhoneSet n = root.residentSet();
		Node w = n.myset.lis.head;
		while(w != null){
			if(((MobilePhone)w.data).id == m.id) break;
			else w = w.next;
		}
		if(w==null)
		{
			//System.out.println("Error - No mobile phone with identifier " + m.id + " found in the network");
			return null;
		}
		else return ((MobilePhone)w.data).location;
	}

	public Exchange lowestRouter(Exchange a, Exchange b){
		if(a != null && b != null){
			if(a == b) return a;
			else {
				Exchange pro = a;
				Exchange parb = b.parent;
				while(pro.isRoot == false){
					Exchange temp = b;
					while(temp.isRoot == false){
						if(temp.id == pro.id) return temp;
						temp = temp.parent;
					}
					pro = pro.parent;
				}
				return pro;
			}
		}
		return null;
	}

	public ExchangeList routeCall(MobilePhone a, MobilePhone b){
		if(a.status && b.status){
			ExchangeList routePath = new ExchangeList();
			Exchange lr = lowestRouter(a.location,b.location);
			Exchange sub = a.location;
			while(sub != lr){
				routePath.l.Insert(sub);
				sub = sub.parent;
			}
			routePath.l.Insert(lr);

			int indexOfLR = routePath.l.IndexOf(lr);
			sub = b.location;
			while(sub != lr){
				routePath.l.InsertAt(sub,indexOfLR+1);
				sub = sub.parent;
			}
			return routePath;
		} else {
			if(a.status == false && b.status == true) System.out.println("Error - Mobile phone with identifier " + a.id + " is currently switched off");
			else if(a.status == true && b.status == false) System.out.println("Error - Mobile phone with identifier " + b.id + " is currently switched off");
			else System.out.println("Error - Mobile phone with identifiers " + a.id + " " + b.id + " are currently switched off");
		}
		return null;
	}

	public void movePhone(MobilePhone a, Exchange b){
		if(a.status){
			Exchange e = a.location;
			while(e.isRoot == false){
				e.residentSet.myset.Delete(a);
				e = e.parent;
			}
			e.residentSet.myset.Delete(a);
			//Exchange sub = b;
			a.location = b;
			while(b.isRoot == false){
				b.residentSet.myset.Insert(a);
				b = b.parent;
			}
			b.residentSet.myset.Insert(a);
		} else {
			System.out.println("Mobile Phone switched off");
		}
	}

	public String performAction(String actionMessage) {
		Scanner s = new Scanner(actionMessage);
		String command = s.next();
		String s1 = actionMessage + ": ";

		try{
			if(command.equals("addExchange")){
				int x = s.nextInt();
				int y = s.nextInt();
				Exchange a = getExchange(x);
				if(a == null){
					System.out.println("no such exchange found");
					return "";
				}
				Exchange b = new Exchange(y);
				a.children.l.Insert(b);
				b.parent = a;
			}

			else if(command.equals("switchOnMobile")){
				int x = s.nextInt();
				int y = s.nextInt();
				Exchange b = getExchange(y);
				if(b == null) System.out.println("Error- No exchange with identifier" + b);
				else {
					Exchange sub = b;
					while(sub.isRoot() == false){
						sub = sub.parent();
					}
					MobilePhoneSet n = sub.residentSet();

					Node w = n.myset.lis.head;
					while(w != null){
						if(((MobilePhone)w.data).id==x) break;
						else w = w.next;
					}
					if(w == null){
						MobilePhone a = new MobilePhone(x);
						switchOn(a,b);
					} else {
						MobilePhone a = (MobilePhone) w.data;
						switchOn(a,b);
					}
				}
			}

			else if(command.equals("switchOffMobile")){
				int x = s.nextInt();
				MobilePhone a = findMobilePhone(x);
				if(a != null) switchOff(a);
			}

			else if(command.equals("queryNthChild")){
				int x = s.nextInt();
				int y = s.nextInt();
				Exchange a = getExchange(x);

				if(a != null){
					Exchange ec = (Exchange) a.child(y);
					int identifier = ec.id;

					s1 = s1 + identifier;
					return s1;
				}
			}

			else if(command.equals("queryMobilePhoneSet")){
				int x = s.nextInt();
				Exchange e = getExchange(x);
				if(e != null){
					Node n = e.residentSet().myset.lis.head;
					if(n == null) System.out.println("empty mobile phone set");
					while(n != null){
						MobilePhone mp = (MobilePhone) n.data;
						if(mp.status) {
							if(n.next == null) s1 = s1 + Integer.toString(mp.id);
							else s1 = s1 + Integer.toString(mp.id) + ", ";
						}
						n = n.next;
					}
					return s1;
				}
			}

			else if(command.equals("findPhone")){
				int x = s.nextInt();
				s1 = "queryFindPhone " + x + ": ";
				MobilePhone mp = findMobilePhone(x);
				if(mp != null) {
					Exchange l = findMobile(mp);
					s1 = s1 + Integer.toString(l.id);
					return s1;
				} else {
					s1 = s1 + "Error - No mobile phone with identifier " + x + " found in the network";
					return s1;
				}
			}

			else if(command.equals("lowestRouter")){
				int x = s.nextInt();
				int y = s.nextInt();
				s1 = "queryLowestRouter " + x + " " + y + ": ";
				Exchange a = getExchange(x);
				Exchange b = getExchange(y);
				if(a != null && b != null) {
					Exchange e = lowestRouter(a,b);
					if(e != null) {
						s1 = s1 + Integer.toString(e.id);
						return s1;
					}
				}
			}

			else if(command.equals("findCallPath")){
				int x = s.nextInt();
				int y = s.nextInt();
				s1 = "queryFindCallPath " + x + " " + y + ": ";
				MobilePhone a = findMobilePhone(x);
				MobilePhone b = findMobilePhone(y);
				if(a != null && b != null) {
					if(a.status && b.status){
						ExchangeList el = routeCall(a,b);
						if(el != null){
							Node printTemp = el.l.head;
					    while(printTemp != null){
					      if(printTemp.next == null) s1 = s1 + ((Exchange)printTemp.data).id;
					      else s1 = s1 + ((Exchange)printTemp.data).id + ", ";
					      printTemp = printTemp.next;
					    }
							return s1;
						}
					} else {
						if(a.status == false && b.status == true) s1 = s1 + "Error - Mobile phone with identifier " + a.id + " is currently switched off";
						else if(a.status == true && b.status == false) s1 = s1 + "Error - Mobile phone with identifier " + b.id + " is currently switched off";
						else s1 = s1 + "Error - Mobile phone with identifiers " + a.id + " " + b.id + " are currently switched off";
						return s1;
					}
				}
			}

			else if(command.equals("movePhone")){
				int x = s.nextInt();
				int y = s.nextInt();
				MobilePhone a = findMobilePhone(x);
				Exchange b = getExchange(y);
				if(a != null && b != null){
					movePhone(a,b);
				} else {
					if(a == null) System.out.println("no such mobile phone");
					else System.out.println("no such exchange");
				}
			}

		} catch(Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
