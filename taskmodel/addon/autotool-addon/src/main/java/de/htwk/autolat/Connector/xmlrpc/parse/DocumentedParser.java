package de.htwk.autolat.Connector.xmlrpc.parse;


import de.htwk.autolat.Connector.types.Documented;

@SuppressWarnings("unused")
public class DocumentedParser<A>
    implements Parser<Documented<A>>
{
    private final Parser<Documented<A>> parser;
    
    public DocumentedParser(final Parser<A> aParser)
    {
        parser = new StructFieldParser<Documented<A>>(
                     "Documented",
                     new Parser<Documented<A>>()
                     {
                         Parser<A> contentsParser = null;
                         Parser<String> documentationParser = null;
                         
                         public Documented<A> parse(Object val) throws ParseErrorBase
                         {
                             if (contentsParser == null) {
                              contentsParser = new StructFieldParser<A>(
                                                    "contents",
                                                    aParser);
                            }
                             if (documentationParser == null) {
                              documentationParser = new StructFieldParser<String>(
                                                         "documentation",
                                                         StringParser.getInstance());
                            }
                             return new Documented<A>(
                                 contentsParser.parse(val),
                                 documentationParser.parse(val));
                         }
                         
                     }
                     
                 );
    }
    
    public Documented<A> parse(Object val) throws ParseErrorBase
    {
        return parser.parse(val);
    }
    
}





/**
 * 
 */
