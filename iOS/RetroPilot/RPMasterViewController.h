//
//  RPMasterViewController.h
//  RetroPilot
//
//  Created by CGK on 20/01/14.
//  Copyright (c) 2014 Cegeka NV. All rights reserved.
//

#import <UIKit/UIKit.h>

#import <CoreData/CoreData.h>

@interface RPMasterViewController : UITableViewController <NSFetchedResultsControllerDelegate>

@property (strong, nonatomic) NSFetchedResultsController *fetchedResultsController;
@property (strong, nonatomic) NSManagedObjectContext *managedObjectContext;

@end
