//
//  RPDetailViewController.h
//  RetroPilot
//
//  Created by CGK on 20/01/14.
//  Copyright (c) 2014 Cegeka NV. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface RPDetailViewController : UIViewController

@property (strong, nonatomic) id detailItem;

@property (weak, nonatomic) IBOutlet UILabel *detailDescriptionLabel;
@end
