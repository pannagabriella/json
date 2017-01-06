package com.company;

public class Main {

    public static void main(String[] args) {
    ArgumentParser argParser = new ArgumentParser(args);

    try{
        argParser.validateArgs();
    }
    catch (IllegalArgumentException e){
        System.out.println(e);
        System.exit(1);
    }

    Downloader d = new Downloader(argParser.linkGenerator() + 1);
    d.download();
    Parser parser;
    if(argParser.getOption().equals(Option.MEMBER_D_COSTS) || argParser.getOption().equals(Option.MEMBER_S_COSTS)){
        parser = new Parser(argParser.getOption(), d.getJsonResult(), argParser.getMemberName());
    }
    else{
        parser = new Parser(argParser.getOption(), d.getJsonResult());
    }

    parser.parseMainJson();

    Parliament parliament = new Parliament(argParser.getCadency());
    parliament.pushMembers(parser.getMembers());
    parliament.generateCosts();
    parliament.show();

    switch(argParser.getOption()){
        case MEMBER_S_COSTS:
            System.out.println(parliament.generateAverageCosts());
            break;
        case MEMBER_D_COSTS:
            System.out.println(parliament.getMemberLittleCosts());
            break;
        case AVERAGE_ALL_COST:
            System.out.println(parliament.generateAverageCosts());
            break;
        default: break;
    }




	// write your code here
    }
}
