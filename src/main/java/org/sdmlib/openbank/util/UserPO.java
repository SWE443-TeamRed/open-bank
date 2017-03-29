package org.sdmlib.openbank.util;

import org.sdmlib.models.pattern.PatternObject;
import org.sdmlib.openbank.User;
import org.sdmlib.models.pattern.AttributeConstraint;
import org.sdmlib.models.pattern.Pattern;
import org.sdmlib.openbank.util.AccountPO;
import org.sdmlib.openbank.Account;
import org.sdmlib.openbank.util.UserPO;
import org.sdmlib.openbank.util.AccountSet;

public class UserPO extends PatternObject<UserPO, User>
{

    public UserSet allMatches()
   {
      this.setDoAllMatches(true);
      
      UserSet matches = new UserSet();

      while (this.getPattern().getHasMatch())
      {
         matches.add((User) this.getCurrentMatch());
         
         this.getPattern().findMatch();
      }
      
      return matches;
   }


   public UserPO(){
      newInstance(null);
   }

   public UserPO(User... hostGraphObject) {
      if(hostGraphObject==null || hostGraphObject.length<1){
         return ;
      }
      newInstance(null, hostGraphObject);
   }

   public UserPO(String modifier)
   {
      this.setModifier(modifier);
   }
   
   //==========================================================================
   
   public boolean openAccount(User p0)
   {
      if (this.getPattern().getHasMatch())
      {
         return ((User) getCurrentMatch()).openAccount(p0);
      }
      return false;
   }

   public UserPO createNameCondition(String value)
   {
      new AttributeConstraint()
      .withAttrName(User.PROPERTY_NAME)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public UserPO createNameCondition(String lower, String upper)
   {
      new AttributeConstraint()
      .withAttrName(User.PROPERTY_NAME)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public UserPO createNameAssignment(String value)
   {
      new AttributeConstraint()
      .withAttrName(User.PROPERTY_NAME)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(Pattern.CREATE)
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public String getName()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((User) getCurrentMatch()).getName();
      }
      return null;
   }
   
   public UserPO withName(String value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((User) getCurrentMatch()).setName(value);
      }
      return this;
   }
   
   public UserPO createUserIDCondition(String value)
   {
      new AttributeConstraint()
      .withAttrName(User.PROPERTY_USERID)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public UserPO createUserIDCondition(String lower, String upper)
   {
      new AttributeConstraint()
      .withAttrName(User.PROPERTY_USERID)
      .withTgtValue(lower)
      .withUpperTgtValue(upper)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public UserPO createUserIDAssignment(String value)
   {
      new AttributeConstraint()
      .withAttrName(User.PROPERTY_USERID)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(Pattern.CREATE)
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public String getUserID()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((User) getCurrentMatch()).getUserID();
      }
      return null;
   }
   
   public UserPO withUserID(String value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((User) getCurrentMatch()).setUserID(value);
      }
      return this;
   }
   
   public UserPO createIsAdminCondition(boolean value)
   {
      new AttributeConstraint()
      .withAttrName(User.PROPERTY_ISADMIN)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(this.getPattern().getModifier())
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public UserPO createIsAdminAssignment(boolean value)
   {
      new AttributeConstraint()
      .withAttrName(User.PROPERTY_ISADMIN)
      .withTgtValue(value)
      .withSrc(this)
      .withModifier(Pattern.CREATE)
      .withPattern(this.getPattern());
      
      super.filterAttr();
      
      return this;
   }
   
   public boolean getIsAdmin()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((User) getCurrentMatch()).isIsAdmin();
      }
      return false;
   }
   
   public UserPO withIsAdmin(boolean value)
   {
      if (this.getPattern().getHasMatch())
      {
         ((User) getCurrentMatch()).setIsAdmin(value);
      }
      return this;
   }
   
   public AccountPO createAccountPO()
   {
      AccountPO result = new AccountPO(new Account[]{});
      
      result.setModifier(this.getPattern().getModifier());
      super.hasLink(User.PROPERTY_ACCOUNT, result);
      
      return result;
   }

   public AccountPO createAccountPO(String modifier)
   {
      AccountPO result = new AccountPO(new Account[]{});
      
      result.setModifier(modifier);
      super.hasLink(User.PROPERTY_ACCOUNT, result);
      
      return result;
   }

   public UserPO createAccountLink(AccountPO tgt)
   {
      return hasLinkConstraint(tgt, User.PROPERTY_ACCOUNT);
   }

   public UserPO createAccountLink(AccountPO tgt, String modifier)
   {
      return hasLinkConstraint(tgt, User.PROPERTY_ACCOUNT, modifier);
   }

   public AccountSet getAccount()
   {
      if (this.getPattern().getHasMatch())
      {
         return ((User) this.getCurrentMatch()).getAccount();
      }
      return null;
   }

}
