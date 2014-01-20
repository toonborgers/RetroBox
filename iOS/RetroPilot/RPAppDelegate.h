//
//  RPAppDelegate.h
//  RetroPilot
//
//  Created by CGK on 20/01/14.
//  Copyright (c) 2014 Cegeka NV. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface RPAppDelegate : UIResponder <UIApplicationDelegate>

@property (strong, nonatomic) UIWindow *window;

@property (readonly, strong, nonatomic) NSManagedObjectContext *managedObjectContext;
@property (readonly, strong, nonatomic) NSManagedObjectModel *managedObjectModel;
@property (readonly, strong, nonatomic) NSPersistentStoreCoordinator *persistentStoreCoordinator;

- (void)saveContext;
- (NSURL *)applicationDocumentsDirectory;

@end
