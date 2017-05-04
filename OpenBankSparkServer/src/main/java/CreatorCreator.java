import de.uniks.networkparser.IdMap;

class CreatorCreator{

   public static IdMap createIdMap(String sessionID)
   {
      IdMap jsonIdMap = new IdMap().withSessionId(sessionID);
      jsonIdMap.with(new UserCreator());
      jsonIdMap.with(new UserPOCreator());
      jsonIdMap.with(new TransactionCreator());
      jsonIdMap.with(new TransactionPOCreator());
      jsonIdMap.with(new AccountCreator());
      jsonIdMap.with(new AccountPOCreator());
      jsonIdMap.with(new BankCreator());
      jsonIdMap.with(new BankPOCreator());
      jsonIdMap.with(new BigIntegerCreator());
      jsonIdMap.with(new BigIntegerPOCreator());
      jsonIdMap.with(new FeeValueCreator());
      jsonIdMap.with(new FeeValuePOCreator());
      return jsonIdMap;
   }
}
