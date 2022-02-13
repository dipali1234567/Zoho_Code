/******************************************************************************

Welcome to GDB Online.
GDB online is an online compiler and debugger tool for C, C++, Python, Java, PHP, Ruby, Perl,
C#, VB, Swift, Pascal, Fortran, Haskell, Objective-C, Assembly, HTML, CSS, JS, SQLite, Prolog.
Code, Compile, Run and Debug online from anywhere in world.

*******************************************************************************/
import java.util.*;

class User{
    public String name;
    public TreeNode role;
    
    
    public User(String name,TreeNode r)
    {
        this.name=name;
         this.role=r;
    }
    
}


class TreeNode
{
    public String role;
    public ArrayList<TreeNode>subroles;
    public ArrayList<User>users;
   public TreeNode parent;
    
   public TreeNode(String role)
    {
        this.parent=null;
        this.role=role;
        this.subroles=new ArrayList<TreeNode>();
        this.users=new ArrayList<User>();
    }
    
    
    
    
    public void addSubRoles(TreeNode subrole)
    {
        
        this.subroles.add(subrole);
        subrole.parent=this;
    }
    
    
    
    
    public void addUser(User u)
    {
        System.out.println("User added "+u.name);
        this.users.add(u);
    }
    
    
    
    public void displayRole()
    {
        if(this==null)
        return;
        Queue<TreeNode> queue=new LinkedList<TreeNode>();
        queue.add(this);
        
        while(!queue.isEmpty())
        {
            TreeNode presentNode=queue.remove();
            System.out.print(presentNode.role+" ");
   
            if(!presentNode.subroles.isEmpty())
               {queue.addAll(presentNode.subroles);}
         }
    }
    
    
    
    
    
    
    public void displayUsers()
    {
        int f=0;
        Queue<TreeNode> queue=new LinkedList<TreeNode>();
        queue.add(this);
        
        while(!queue.isEmpty())
        {
            TreeNode presentNode=queue.remove();
        
        
         if(!presentNode.users.isEmpty())
       
          {
              f=1;
           for(int i=0;i<presentNode.users.size();i++)
          System.out.println(presentNode.users.get(i).name+"-"+presentNode.role);
           }
     
  
        if(!presentNode.subroles.isEmpty())
           queue.addAll(presentNode.subroles);
      }
      if(f==0)
      {
          System.out.println("No user added till now");
      }
      
    }
    
  
     
    
    
    public TreeNode getN(String r)
    {
      if(this==null)
      return null;
      
        Queue<TreeNode> queue=new LinkedList<TreeNode>();
        
        queue.add(this);
        
        while(!queue.isEmpty())
        {
            TreeNode presentNode=queue.remove();
        
        if(r.equalsIgnoreCase(presentNode.role))
        return presentNode;
        
        if(!presentNode.subroles.isEmpty())
        queue.addAll(presentNode.subroles);
        }
        
        return null;
    }
    
    
    public int getTreeHeight(TreeNode root)
    {
        int h=0;
        if(root==null)
        return h;
        
        if(root.subroles==null)
        return 1;
        
        for(TreeNode subrole : root.subroles)
        {
            h=Math.max(h,getTreeHeight(subrole));
        }
        return h+1;
    }
    
    
    
    public User getUserNode(String userName)
    {
        if(this==null)
        {
            System.out.println("Heirarchy not exist hence user not exist");
            return null;
        }
        
        
        Queue<TreeNode>queue=new LinkedList<TreeNode>();
        queue.add(this);
        while(!queue.isEmpty())
        {
            TreeNode presentNode=queue.remove();
            if(!presentNode.users.isEmpty())
            for(int i=0;i<presentNode.users.size();i++)
            {
                if(presentNode.users.get(i).name.equalsIgnoreCase(userName))
                return presentNode.users.get(i);
            }
            
            queue.addAll(presentNode.subroles);
        }
        
        System.out.println("User not exist");
        return null;
    }
    
   
    
    
    public void deleteRole(String dNode,String tNode)
    {
        if(this==null)
        return;
        
           TreeNode transferNode=this.getN(tNode);
           if(transferNode==null)
            transferNode=new TreeNode(tNode);
           TreeNode deleteNode=this.getN(dNode);
        
          if(!deleteNode.subroles.isEmpty())
                  transferNode.subroles.addAll(deleteNode.subroles);
               
              
           if(!deleteNode.users.isEmpty())  
                 transferNode.users.addAll(deleteNode.users);
         
             Queue<TreeNode> queue=new LinkedList<TreeNode>();
        

          if(transferNode.subroles.contains(transferNode))
                   transferNode.subroles.remove(transferNode);
    
           else{                   
        
                  queue.add(this);
        
               while(!queue.isEmpty())
              {
                  TreeNode presentNode =queue.remove();
                    if(presentNode.subroles.contains(transferNode))
                   {
                        presentNode.subroles.remove(transferNode);
                         break; 
                    }
            
                    queue.addAll(presentNode.subroles);
               }
        
              }
        
        queue.clear();
        queue.add(this);
        while(!queue.isEmpty())
        {
            TreeNode presentNode = queue.remove();
            if(presentNode.subroles.contains(deleteNode))
            {
               presentNode.subroles.remove(deleteNode);
               presentNode.subroles.add(transferNode);
               break;
            }
            
                queue.addAll(presentNode.subroles);
         }
        
        
    }
    
    
    public void deleteUser(String userName)
    {
        if(this==null)
        {
            System.out.println("Heirarchy not exist hence node can't be deleted");
            return ;
        }
        User user=this.getUserNode(userName);
        if(user!=null)
        {
            Queue<TreeNode>queue=new LinkedList<TreeNode>();
            queue.add(this);
            while(!queue.isEmpty())
            {
                TreeNode presentNode=queue.remove();
                if(!presentNode.users.isEmpty())
                if(presentNode.users.contains(user))
                   {
                       presentNode.users.remove(user);
                       return ;
                   }
                   
                   queue.addAll(presentNode.subroles);
            }
            
            this.displayUsersAndSubUsers();
            
        }
    }
    
    public String getCommanBoss(String fuser,String suser)
    {
        User fiuser=this.getUserNode(fuser);
        User seuser=this.getUserNode(suser);
        
        if(fiuser!=null&&seuser!=null){
        TreeNode pf=fiuser.role.parent;
        
        
        while(pf!=null)
        {
            
            TreeNode ps=seuser.role.parent;
            while(ps!=null)
            {
                if((ps==pf)&&!(ps.users.isEmpty()))
               return ps.users.get(0).name;
               
               ps=ps.parent;
            }
            
            pf=pf.parent;
        }
        
        
    }
    System.out.println("No common Boss is there");
      return null;
    
    }
    
    
    
    public int UsersFromTop(String user)
    {
        int topUsers=0;
       User userNode=this.getUserNode(user);
       if(userNode!=null)
       {
          TreeNode mid=userNode.role.parent;
          
          while(mid!=null)
          {
              if(!mid.users.isEmpty())
              topUsers+=mid.users.size();
              mid=mid.parent;
          }
       
       
       }
       return topUsers;
    }
    
    
    public void displayUsersAndSubUsers()
    {
        if(this==null)
        return;
        
        Queue<TreeNode>queue=new LinkedList<TreeNode>();
        queue.add(this);
        
        while(!queue.isEmpty())
        {
            TreeNode p=queue.remove();
            
            if(!p.users.isEmpty())
            {
               // System.out.print(p.users.get(0).name+"-");
                Queue<TreeNode>q=new LinkedList<TreeNode>();
                q.addAll(p.subroles);
                ArrayList<User>temp=new ArrayList<User>();
                temp.clear();
                while(!q.isEmpty())
                {
                    TreeNode t=q.remove();
                    if(!t.users.isEmpty())
                    temp.addAll(t.users);
                    q.addAll(t.subroles);
                }
                
               
                    for(User user:p.users){
                    System.out.print(user.name+"-");
                    
                    if(!temp.isEmpty()){
                        for(User au:temp)
                        System.out.print(au.name+" ");}
                      System.out.println();    
                     }
                
                temp.clear();
            }
            
            queue.addAll(p.subroles);
        }
        
    }
    
    
}




public class Main
{
	public static void main(String[] args) {
		int op=0;
		Scanner sc=new Scanner(System.in);
	    System.out.println("Enter root role");
	  
	    TreeNode root=new TreeNode(sc.nextLine());
	    System.out.println(root.role);
	    
	    System.out.println("Operations :\n");
	    do
	    {
	     System.out.println("  1. Add Sub Role \n  2. Display Roles\n  3. Delete Role \n  4. Add User\n  5. Dispaly Users");
	     System.out.println("  6. Display Users and Sub Users\n  7. Delete User\n  8. Number of users from top\n  9. Height of role hierachy");
	     
	     System.out.println("  10. Common boss of users\n  11. Exit");

System.out.println("\n Operation to be performed");
              op=sc.nextInt();
              sc.nextLine();
            switch(op)
             {
                case 1:System.out.println("Enter Subrole Name");
           
                 TreeNode sub=new TreeNode(sc.nextLine());
                  System.out.println("Enter Reporting role");
                  String ReportingRole=sc.nextLine();
                   TreeNode rp=root.getN(ReportingRole);
                 if(rp!=null)
                    {  rp.addSubRoles(sub);}
                 else
                    System.out.println("Reporting node is not exist");
                 break;
           
                case 2:  root.displayRole();
                         break;
                         
                 case 3 : System.out.println("Enter the role to be deleted :");
                        String dNode=sc.nextLine();
                        System.out.println("Enter the role to be transferred :");
                        String tNode=sc.nextLine();
                        root.deleteRole(dNode,tNode);
                         break;
             
               case 4:
        
                  System.out.println("Enter User Name");
                 String name=sc.nextLine();
                  System.out.println("Enter the role");
                   String h=sc.nextLine();
                  TreeNode m=root.getN(h);
                  
                     User u=new User(name,m);
                       if(u!=null)
                          m.addUser(u);
                   break;
         
                 case 5: root.displayUsers();
                         break;
                         
                case 6:root.displayUsersAndSubUsers();
                         break;         
                         
                 case 7:System.out.println("Enter username to be deleted :");
                      root.deleteUser(sc.nextLine());
                      break;    
                      
                      
                    case 8: System.out.println("Enter user Name");
                       System.out.println( "Number of users from top :"+root.UsersFromTop(sc.nextLine()));
                        break;
                        
                         
                   case 9:      System.out.println("Height :"+root.getTreeHeight(root));
                                 break;
                
               
                    case 10 : System.out.println("Enter user 1 :");
                        String fuser=sc.nextLine();
                        System.out.println("Enter user 2 :");
                        String suser=sc.nextLine();
                        if(root.getCommanBoss(fuser,suser)!=null)
                        System.out.println(root.getCommanBoss(fuser,suser));
                        break;     
                         
                
                         
                default : System.out.println("Invalid Entry"); break;
                      
                
                
               
}
	    }while(op!=11);
	
	}
}







