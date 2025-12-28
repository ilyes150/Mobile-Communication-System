# Mobile Communication System Documentation

A comprehensive Java-based telecommunications simulation system that models mobile phones, SIM cards, cell towers (antennas), and network infrastructure.

---

## Table of Contents

1. [System Overview](#system-overview)
2. [Phone Class](#phone-class)
3. [SimCard Class](#simcard-class)
4. [Antenna Class](#antenna-class)
5. [Network Class](#network-class)

---

## System Overview

This Mobile Communication System simulates a mobile network with the following components:

- **Phones**: Mobile devices that can make and receive calls
- **SIM Cards**: Identity and credit management for phones
- **Antennas**: Cell towers providing network coverage
- **Network**: Infrastructure managing all antennas

### Key Features

- Geographic positioning and movement
- Call management with credit deduction
- Battery level monitoring
- Network coverage validation
- Antenna capacity management
- Automatic call termination when leaving coverage

---

## Phone Class

### Overview

The `Phone` class represents a mobile phone in the telecommunications simulation system. It manages phone calls, battery levels, SIM card integration, network connectivity through antennas, and geographic positioning.

### Class Attributes

#### Instance Variables

- **`x, y`** (double): Geographic coordinates of the phone's current position
- **`battery`** (double): Current battery level of the phone
- **`inCall`** (boolean): Indicates whether the phone is currently in an active call
- **`sim`** (SimCard): The SIM card associated with this phone
- **`callPartner`** (Phone): Reference to the phone currently in a call with this phone
- **`connectedAntenna`** (Antenna): The nearest antenna currently serving this phone

#### Static Variables

- **`network`** (Network): Shared network infrastructure used by all phones

#### Constants

- **`MIN_BATTERY`** = 5.0: Minimum battery level required to make or receive calls
- **`CALL_COST`** = 4.0: Credit cost deducted from the SIM card per call

### Constructor

#### `Phone(double x, double y, SimCard sim, double battery)`

Creates a new Phone instance with specified location, SIM card, and battery level.

**Parameters:**
- `x`: Initial x-coordinate position
- `y`: Initial y-coordinate position
- `sim`: SIM card to be assigned to this phone
- `battery`: Initial battery level

**Behavior:**
- Assigns the SIM card to this phone
- Initializes call status as inactive
- Finds and connects to the nearest antenna in the network

### Methods

#### Static Methods

##### `setNetwork(Network net)`

Sets the network infrastructure used by all Phone instances.

**Parameters:**
- `net`: The Network object to be used by all phones

**Important:** Must be called before creating any Phone instances.

#### Instance Methods

##### `canMakeCall()`

Checks if the phone can initiate a call.

**Returns:** `boolean` - true if all conditions are met, false otherwise

**Validation Checks:**
- Phone is not already in a call
- Battery level is sufficient (≥ MIN_BATTERY)
- SIM card is active
- SIM card has enough credit (≥ CALL_COST)

**Side Effects:** Prints error messages to console when conditions are not met

##### `canReceiveCall()`

Checks if the phone can receive an incoming call.

**Returns:** `boolean` - true if phone can receive, false otherwise

**Validation Checks:**
- Phone is not already in a call
- Battery level is sufficient (≥ MIN_BATTERY)

**Side Effects:** Prints error messages to console when conditions are not met

##### `call(Phone receiver)`

Initiates a call to another phone.

**Parameters:**
- `receiver`: The Phone object to call

**Behavior:**
1. Validates both caller and receiver can participate in the call
2. Locates nearest antennas for both phones
3. Verifies network availability for both phones
4. Establishes connection between phones
5. Updates call status and partners for both phones
6. Increments active call count on both antennas
7. Deducts call cost from caller's SIM card
8. Prints call status message

**Side Effects:** 
- Updates call state for both phones
- Modifies antenna active call counters
- Deducts credit from SIM card
- Prints status messages to console

##### `endCall()`

Terminates the current call.

**Behavior:**
1. Checks if phone is in an active call
2. Ends call for both the current phone and its partner
3. Decrements active call count on both antennas
4. Resets call state (inCall flag and callPartner reference)
5. Prints confirmation message

**Side Effects:**
- Updates call state for both phones
- Modifies antenna active call counters
- Prints status message to console

##### `move(double x, double y)`

Updates the phone's geographic location.

**Parameters:**
- `x`: New x-coordinate position
- `y`: New y-coordinate position

**Behavior:**
1. Updates phone coordinates
2. Finds and connects to nearest antenna at new location
3. If in a call and no antenna is available, ends the call automatically

**Side Effects:**
- Updates phone position
- May change connected antenna
- May terminate active call if network is unavailable
- Prints message if call is dropped due to lost connection

### Dependencies

- **SimCard**: Manages phone number and credit balance
- **Antenna**: Represents network infrastructure points
- **Network**: Manages collection of antennas and finds nearest available antenna

### Notes

- The network must be set using `setNetwork()` before creating any Phone instances
- Call costs are deducted immediately when a call is initiated
- Moving out of network range during a call will automatically terminate the call
- Both phones in a call share responsibility for maintaining the connection

---

## SimCard Class

### Overview

The `SimCard` class represents a SIM (Subscriber Identity Module) card in a telecommunications simulation system. It manages the phone number, activation status, credit balance, and the association with a Phone device.

### Class Attributes

#### Instance Variables

- **`number`** (String, final): Unique phone number associated with this SIM card (immutable)
- **`active`** (boolean): Indicates whether the SIM card is currently active and able to make/receive calls
- **`credit`** (double): Current credit balance available for making calls
- **`owner`** (Phone): Reference to the Phone object this SIM card is assigned to

### Constructor

#### `SimCard(double credit, String number)`

Creates a new SimCard instance with specified credit balance and phone number.

**Parameters:**
- `credit`: Initial credit amount for the SIM card
- `number`: Unique phone number identifier

**Behavior:**
- Sets the phone number (immutable after creation)
- Initializes credit to the maximum of 0 or the provided amount (prevents negative credit)
- Sets the SIM card as active by default
- Initializes with no phone owner

### Methods

#### Assignment Methods

##### `assignToPhone(Phone phone)`

Assigns this SIM card to a Phone device.

**Parameters:**
- `phone`: The Phone object to assign this SIM card to

**Behavior:**
- If the SIM card has no owner, assigns the phone as the owner
- If already assigned, prints an error message and prevents reassignment

**Side Effects:**
- Updates the owner reference
- Prints error message if reassignment is attempted

**Note:** A SIM card can only be assigned to one phone at a time

#### Getter Methods

##### `getOwner()`

Returns the Phone object this SIM card is assigned to.

**Returns:** `Phone` - The owner phone, or null if not assigned

##### `getCredit()`

Returns the current credit balance.

**Returns:** `double` - Current credit amount

##### `isActive()`

Returns the activation status of the SIM card.

**Returns:** `boolean` - true if active, false if inactive

##### `getNumber()`

Returns the phone number associated with this SIM card.

**Returns:** `String` - The phone number

#### Setter Methods

##### `setActive(boolean active)`

Sets the activation status of the SIM card.

**Parameters:**
- `active`: New activation status (true for active, false for inactive)

**Usage:** Used to activate or deactivate the SIM card

##### `addCredit(double amount)`

Adds credit to the SIM card balance.

**Parameters:**
- `amount`: Amount of credit to add

**Behavior:**
- Only adds credit if the amount is positive
- Ignores zero or negative amounts

**Note:** This method does not return a success/failure indicator

#### Credit Management Methods

##### `hasEnoughCredit(double amount)`

Checks if the SIM card has sufficient credit for a transaction.

**Parameters:**
- `amount`: Required credit amount to check

**Returns:** `boolean` - true if credit balance is sufficient, false otherwise

##### `deductCredit(double amount)`

Deducts a specified amount from the credit balance.

**Parameters:**
- `amount`: Amount of credit to deduct

**Behavior:**
- Only deducts credit if sufficient balance exists
- Does nothing if insufficient credit (silent failure)

**Note:** Always check with `hasEnoughCredit()` before calling if you need to verify success

### Design Patterns & Features

#### Immutability
- The phone number is **final** and cannot be changed after creation
- This ensures the SIM card's identity remains constant

#### Validation
- Credit cannot be initialized to negative values (automatically set to 0 if negative)
- Credit can only be added with positive amounts
- Credit is only deducted if sufficient balance exists

#### Single Assignment
- A SIM card can only be assigned to one phone
- Attempted reassignment is prevented and logged

### Integration with Phone Class

The SimCard class is tightly integrated with the Phone class:
- The Phone constructor automatically calls `assignToPhone()` to establish the relationship
- The Phone class checks SIM status (`isActive()`) before making calls
- The Phone class verifies credit availability (`hasEnoughCredit()`) before initiating calls
- The Phone class deducts credit (`deductCredit()`) when calls are made

### Notes

- Credit deduction is silent on failure; use `hasEnoughCredit()` to verify before deducting if you need confirmation
- Once assigned to a phone, a SIM card cannot be reassigned without error
- Inactive SIM cards cannot be used to make calls (checked by the Phone class)
- The phone number format is not validated; any string can be used

---

## Antenna Class

### Overview

The `Antenna` class represents a cell tower or base station in a telecommunications simulation system. It provides network coverage within a defined radius and manages the number of active calls it can handle simultaneously based on its capacity.

### Class Attributes

#### Instance Variables

- **`x`** (double, final): X-coordinate of the antenna's geographic position (immutable)
- **`y`** (double, final): Y-coordinate of the antenna's geographic position (immutable)
- **`radius`** (double, final): Coverage radius of the antenna in distance units (immutable)
- **`activeCalls`** (int): Current number of active calls being handled by this antenna
- **`capacity`** (int, final): Maximum number of simultaneous calls this antenna can handle (immutable)

### Constructor

#### `Antenna(double x, double y, double radius, int capacity)`

Creates a new Antenna instance at a specified location with defined coverage and capacity.

**Parameters:**
- `x`: X-coordinate position of the antenna
- `y`: Y-coordinate position of the antenna
- `radius`: Coverage radius (distance from antenna center)
- `capacity`: Maximum number of simultaneous calls

**Behavior:**
- Sets the antenna's fixed position
- Defines the coverage area
- Sets the call capacity limit
- Initializes active call count to 0

### Methods

#### Getter Methods

##### `getX()`

Returns the X-coordinate of the antenna's position.

**Returns:** `double` - X-coordinate value

##### `getY()`

Returns the Y-coordinate of the antenna's position.

**Returns:** `double` - Y-coordinate value

##### `getRadius()`

Returns the coverage radius of the antenna.

**Returns:** `double` - Coverage radius value

##### `getCapacity()`

Returns the maximum call capacity of the antenna.

**Returns:** `int` - Maximum number of simultaneous calls

#### Capacity Management Methods

##### `canAcceptNewCall()`

Checks if the antenna has available capacity for a new call.

**Returns:** `boolean` - true if active calls are below capacity, false if at full capacity

**Usage:** Should be called before incrementing active calls to ensure capacity limits are respected

##### `incrementActiveCalls()`

Increments the active call counter by one.

**Behavior:**
- Only increments if the antenna can accept new calls (below capacity)
- Silently does nothing if already at full capacity

**Safety:** Built-in capacity check prevents exceeding the maximum limit

##### `decrementActiveCalls()`

Decrements the active call counter by one.

**Behavior:**
- Only decrements if active calls count is greater than zero
- Prevents negative call counts

**Usage:** Called when a call ends or is terminated

#### Coverage Methods

##### `isInCoverage(double px, double py)`

Determines if a given point is within the antenna's coverage area.

**Parameters:**
- `px`: X-coordinate of the point to check
- `py`: Y-coordinate of the point to check

**Returns:** `boolean` - true if the point is within coverage radius, false otherwise

**Algorithm:**
- Calculates Euclidean distance between the antenna and the given point
- Formula: `distance = √((px - x)² + (py - y)²)`
- Returns true if distance ≤ radius

### Design Patterns & Features

#### Immutability
- Position coordinates (x, y), radius, and capacity are **final**
- Once created, an antenna's location and specifications cannot be changed
- This represents the physical reality that cell towers are fixed infrastructure

#### Thread-Safety Considerations
- The class is **not thread-safe** for concurrent call management
- Multiple simultaneous modifications to `activeCalls` could lead to race conditions
- Consider synchronization if used in multi-threaded environments

#### Defensive Programming
- `incrementActiveCalls()` prevents exceeding capacity
- `decrementActiveCalls()` prevents negative call counts
- These safeguards protect against logic errors in calling code

### Integration with Other Classes

#### With Phone Class
- Phone objects check if they're in coverage using `isInCoverage()`
- Phones increment/decrement call counts when starting/ending calls
- If a phone moves out of coverage during a call, the call is terminated

#### With Network Class
- Network uses `isInCoverage()` to find suitable antennas for phones
- Network may use `canAcceptNewCall()` to find antennas with available capacity
- Multiple antennas are managed by the Network to provide complete coverage

### Mathematical Note

The coverage calculation uses the **Euclidean distance formula**:

```
distance = √((px - x)² + (py - y)²)
```

This creates a **circular coverage area** around the antenna. Points exactly on the boundary (distance = radius) are considered within coverage.

### Notes

- Antennas are stationary and cannot move after creation
- Coverage is circular with no obstacles or terrain considerations
- The capacity limit represents the maximum concurrent calls, not total calls over time
- Attempting to exceed capacity is silently ignored (no error thrown)
- Distance calculations assume a flat 2D plane coordinate system

---

## Network Class

### Overview

The `Network` class represents a telecommunications network infrastructure that manages multiple antennas. It enforces network topology rules by ensuring antennas have overlapping or adjacent coverage areas, and provides functionality to find the nearest available antenna for phones.

### Class Attributes

#### Instance Variables

- **`antennas`** (List<Antenna>, final): Collection of all antennas in the network (the list reference is immutable, but contents can be modified)

### Constructor

The class uses the **default constructor** (no explicit constructor defined).

**Behavior:**
- Initializes an empty ArrayList of antennas
- Network starts with no antennas and they must be added using `addAntenna()`

### Methods

#### `addAntenna(double x, double y, double radius, int capacity)`

Adds a new antenna to the network with validation to ensure network connectivity.

**Parameters:**
- `x`: X-coordinate position of the new antenna
- `y`: Y-coordinate position of the new antenna
- `radius`: Coverage radius of the new antenna
- `capacity`: Maximum number of simultaneous calls

**Behavior:**

1. **First Antenna**: If the network is empty, adds the antenna immediately
2. **Subsequent Antennas**: Checks if the new antenna's coverage overlaps or is adjacent to at least one existing antenna
   - Calculates distance between new antenna and each existing antenna
   - Compares distance to the sum of their radii
   - If `distance ≤ (existingRadius + newRadius)`, antennas are connected
3. **Success**: Adds antenna and prints confirmation message
4. **Failure**: If coverage is completely separate, rejects the antenna and prints error message

**Connectivity Rule:**

The condition `distance ≤ radius1 + radius2` ensures:
- Overlapping coverage areas are accepted
- Adjacent coverage areas (touching at boundaries) are accepted
- Separate, non-contiguous coverage areas are rejected

**Side Effects:**
- Adds antenna to the network on success
- Prints status messages to console

**Returns:** `void` (success/failure indicated by console message only)

**Example Scenarios:**

```
Scenario 1: Overlapping coverage (ACCEPTED)
Antenna A at (0,0) with radius 50
Antenna B at (40,0) with radius 50
Distance = 40, Sum of radii = 100
Result: 40 ≤ 100 → Added

Scenario 2: Adjacent coverage (ACCEPTED)
Antenna A at (0,0) with radius 50
Antenna B at (100,0) with radius 50
Distance = 100, Sum of radii = 100
Result: 100 ≤ 100 → Added

Scenario 3: Separate coverage (REJECTED)
Antenna A at (0,0) with radius 50
Antenna B at (150,0) with radius 50
Distance = 150, Sum of radii = 100
Result: 150 > 100 → Not added
```

#### `findNearestAntenna(double px, double py)`

Finds the nearest antenna that can serve a phone at the given position.

**Parameters:**
- `px`: X-coordinate of the phone's position
- `py`: Y-coordinate of the phone's position

**Returns:** `Antenna` - The nearest suitable antenna, or `null` if none available

**Selection Criteria:**

The method finds antennas that satisfy ALL of the following:
1. **Coverage**: Point must be within the antenna's coverage radius
2. **Capacity**: Antenna must have available capacity for new calls
3. **Distance**: Among qualifying antennas, selects the closest one

**Algorithm:**

1. Initialize tracking variables for nearest antenna and minimum distance
2. Iterate through all antennas in the network
3. Skip antennas that don't cover the position
4. Skip antennas at full capacity
5. Calculate Euclidean distance to qualifying antennas
6. Track the antenna with minimum distance
7. Return the nearest qualifying antenna (or null if none found)

**Distance Calculation:**

Uses the Euclidean distance formula:
```
distance = √((px - antennaX)² + (py - antennaY)²)
```

**Null Return Cases:**
- No antennas in coverage range
- All antennas in coverage are at full capacity
- Network has no antennas

### Design Patterns & Features

#### Network Topology Validation

The network enforces a **connected graph topology**:
- All antennas must have overlapping or adjacent coverage
- Prevents isolated coverage islands
- Ensures phones can roam within the network without losing connectivity
- Creates a contiguous service area

#### Nearest Neighbor Selection

Uses a **greedy nearest-neighbor algorithm**:
- Linear search through all antennas: O(n) complexity
- Prioritizes proximity for optimal signal quality
- Considers both coverage and capacity constraints

#### Defensive Programming

- Returns `null` when no suitable antenna is found (caller must handle this)
- Skips antennas that don't meet criteria rather than throwing exceptions
- First antenna is always accepted to bootstrap the network

### Integration with Other Classes

#### With Phone Class

- Phone class sets the network using `Phone.setNetwork(network)`
- Phones call `findNearestAntenna()` during:
  - Initial connection when created
  - Movement to new positions
  - Call initiation
- Phones handle `null` returns by denying calls or ending active calls

#### With Antenna Class

- Creates new Antenna instances in `addAntenna()`
- Uses `isInCoverage()` to check if phones are in range
- Uses `canAcceptNewCall()` to verify capacity availability
- Uses getters to access antenna position for distance calculations

### Performance Considerations

#### Time Complexity

- **addAntenna**: O(n) where n is the number of existing antennas
- **findNearestAntenna**: O(n) where n is the number of antennas

#### Optimization Opportunities

For large networks, consider:
- Spatial indexing (quadtree, R-tree) for faster proximity queries
- Caching nearest antenna for frequently queried positions
- Load balancing across antennas with similar distances

### Limitations

- **No antenna removal**: Once added, antennas cannot be removed
- **No capacity monitoring**: Network doesn't track overall capacity or load
- **Simple connectivity**: Only checks pairwise distance, not overall network connectivity
- **No handoff logic**: Phones simply reconnect to nearest antenna on movement

### Notes

- The first antenna is always accepted regardless of position
- Subsequent antennas must connect to the existing network
- Distance calculations use Euclidean geometry (flat 2D plane)
- Antennas at capacity are excluded from `findNearestAntenna()` results
- The network doesn't verify that phones can maintain connectivity while moving
- Multiple phones can share the same antenna up to its capacity limit

---

## Conclusion

This Mobile Communication System provides a realistic simulation of mobile network operations with proper validation, resource management, and error handling. The modular design allows for easy extension and modification while maintaining system integrity through defensive programming practices.